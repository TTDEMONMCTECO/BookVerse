package com.example.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.data.local.AppThemeMode
import com.example.data.local.SettingsManager
import com.example.data.model.Book
import com.example.data.model.ReadingStatus
import com.example.data.repository.BookRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class CategoryFilter(
    val id: String,
    val title: String,
    val query: String
)

data class GenreItem(
    val name: String,
    val subjectParam: String,
    val iconEmoji: String
)

val SEARCH_GENRES = listOf(
    GenreItem("Fiction", "fiction", "📖"),
    GenreItem("Fantasy", "fantasy", "🔮"),
    GenreItem("Sci-Fi", "science fiction", "🚀"),
    GenreItem("Mystery", "mystery", "🔍"),
    GenreItem("Thriller", "thriller", "⚡"),
    GenreItem("Romance", "romance", "💖"),
    GenreItem("Technology", "computers", "💻"),
    GenreItem("Science", "science", "🔬"),
    GenreItem("History", "history", "🏛️"),
    GenreItem("Biography", "biography", "👤"),
    GenreItem("Business", "business", "📈"),
    GenreItem("Psychology", "psychology", "🧠"),
    GenreItem("Self-Help", "self-help", "🌱"),
    GenreItem("Philosophy", "philosophy", "⚖️"),
    GenreItem("Art & Design", "art", "🎨"),
    GenreItem("Poetry", "poetry", "✍️"),
    GenreItem("Cooking", "cooking", "🍳")
)

data class DiscoverUiState(
    val isLoading: Boolean = false,
    val featuredBooks: List<Book> = com.example.data.SampleBooksData.sampleBooks.take(8),
    val categoryBooks: List<Book> = com.example.data.SampleBooksData.sampleBooks.drop(2).take(8),
    val activeCategory: CategoryFilter = DEFAULT_CATEGORIES.first(),
    val errorMessage: String? = null
)

data class SearchUiState(
    val query: String = "",
    val selectedGenre: GenreItem? = null,
    val isLoading: Boolean = false,
    val books: List<Book> = emptyList(),
    val sortBy: String = "relevance", // "relevance" or "newest"
    val filterType: String = "all", // "all", "ebooks", "free-ebooks"
    val printType: String = "books", // "books", "all"
    val errorMessage: String? = null,
    val hasSearched: Boolean = false
)

data class DetailUiState(
    val book: Book? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isFavorite: Boolean = false
)

data class BookshelfStats(
    val total: Int = 0,
    val wantToRead: Int = 0,
    val reading: Int = 0,
    val completed: Int = 0
)

val DEFAULT_CATEGORIES = listOf(
    CategoryFilter("fiction", "Fiction & Fantasy", "subject:fiction"),
    CategoryFilter("tech", "Tech & Coding", "computer science programming"),
    CategoryFilter("science", "Science & Space", "astrophysics quantum science"),
    CategoryFilter("psychology", "Psychology & Mind", "psychology habit behavioral"),
    CategoryFilter("design", "Design & Art", "graphic design architecture art"),
    CategoryFilter("business", "Business & Startups", "business innovation startup"),
    CategoryFilter("philosophy", "Philosophy", "philosophy stoicism ethics"),
    CategoryFilter("history", "History & Bio", "biography world history")
)

class BookViewModel(
    private val repository: BookRepository,
    private val settingsManager: SettingsManager
) : ViewModel() {

    val themeMode: StateFlow<AppThemeMode> = settingsManager.themeMode
    val maxResults: StateFlow<Int> = settingsManager.maxResults
    val autoSearchEnabled: StateFlow<Boolean> = settingsManager.autoSearch

    private val _discoverState = MutableStateFlow(DiscoverUiState())
    val discoverState: StateFlow<DiscoverUiState> = _discoverState.asStateFlow()

    private val _searchState = MutableStateFlow(SearchUiState())
    val searchState: StateFlow<SearchUiState> = _searchState.asStateFlow()

    private val _detailState = MutableStateFlow(DetailUiState())
    val detailState: StateFlow<DetailUiState> = _detailState.asStateFlow()

    // Favorites filter
    val favoriteStatusFilter = MutableStateFlow<ReadingStatus?>(null)
    val favoriteSearchFilter = MutableStateFlow("")

    val favoritesState: StateFlow<List<Book>> = combine(
        repository.favorites,
        favoriteStatusFilter,
        favoriteSearchFilter
    ) { allFavs, statusFilter, query ->
        allFavs.filter { book ->
            val matchesStatus = statusFilter == null || book.readingStatus == statusFilter
            val matchesQuery = query.isBlank() || 
                book.title.contains(query, ignoreCase = true) || 
                book.displayAuthors.contains(query, ignoreCase = true)
            matchesStatus && matchesQuery
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val bookshelfStats: StateFlow<BookshelfStats> = repository.favorites.map { books ->
        BookshelfStats(
            total = books.size,
            wantToRead = books.count { it.readingStatus == ReadingStatus.WANT_TO_READ },
            reading = books.count { it.readingStatus == ReadingStatus.READING },
            completed = books.count { it.readingStatus == ReadingStatus.COMPLETED }
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), BookshelfStats())

    private val _userMessage = MutableSharedFlow<String>()
    val userMessage: SharedFlow<String> = _userMessage.asSharedFlow()

    private var searchJob: Job? = null

    init {
        loadDiscoverContent()
    }

    fun setThemeMode(mode: AppThemeMode) {
        settingsManager.setThemeMode(mode)
        viewModelScope.launch {
            _userMessage.emit("Applied ${mode.title}")
        }
    }

    fun setMaxResults(count: Int) {
        settingsManager.setMaxResults(count)
    }

    fun setAutoSearch(enabled: Boolean) {
        settingsManager.setAutoSearch(enabled)
    }

    fun clearAllFavorites() {
        viewModelScope.launch {
            val all = favoritesState.value
            all.forEach { book ->
                repository.removeFavoriteBook(book.id)
            }
            _userMessage.emit("Bookshelf cleared")
        }
    }

    fun loadDiscoverContent() {
        viewModelScope.launch {
            _discoverState.value = _discoverState.value.copy(isLoading = true, errorMessage = null)
            
            // Load featured books
            val featuredResult = repository.searchBooks(
                query = "bestsellers fiction",
                maxResults = 8,
                orderBy = "relevance"
            )

            // Load selected category books
            val activeCategory = _discoverState.value.activeCategory
            val categoryResult = repository.searchBooks(
                query = activeCategory.query,
                maxResults = 12,
                orderBy = "relevance"
            )

            val featuredList = featuredResult.getOrDefault(emptyList()).ifEmpty {
                com.example.data.SampleBooksData.sampleBooks.take(8)
            }
            val categoryList = categoryResult.getOrDefault(emptyList()).ifEmpty {
                com.example.data.SampleBooksData.filterSampleBooks(genre = activeCategory.title).ifEmpty {
                    com.example.data.SampleBooksData.sampleBooks.drop(2).take(8)
                }
            }

            _discoverState.value = _discoverState.value.copy(
                isLoading = false,
                featuredBooks = featuredList,
                categoryBooks = categoryList,
                errorMessage = null
            )
        }
    }

    fun selectCategory(category: CategoryFilter) {
        if (_discoverState.value.activeCategory == category && _discoverState.value.categoryBooks.isNotEmpty()) {
            return
        }
        _discoverState.value = _discoverState.value.copy(activeCategory = category, isLoading = true)
        viewModelScope.launch {
            val result = repository.searchBooks(
                query = category.query,
                maxResults = 12,
                orderBy = "relevance"
            )
            val books = result.getOrDefault(emptyList()).ifEmpty {
                com.example.data.SampleBooksData.filterSampleBooks(genre = category.title).ifEmpty {
                    com.example.data.SampleBooksData.sampleBooks.take(6)
                }
            }
            _discoverState.value = _discoverState.value.copy(
                isLoading = false,
                categoryBooks = books,
                errorMessage = null
            )
        }
    }

    fun onSearchQueryChanged(newQuery: String) {
        _searchState.value = _searchState.value.copy(query = newQuery)
        searchJob?.cancel()
        if (settingsManager.autoSearch.value && newQuery.trim().length >= 3) {
            searchJob = viewModelScope.launch {
                delay(600)
                executeSearch()
            }
        }
    }

    fun selectSearchGenre(genre: GenreItem?) {
        val currentGenre = _searchState.value.selectedGenre
        val newGenre = if (currentGenre == genre) null else genre
        _searchState.value = _searchState.value.copy(selectedGenre = newGenre)
        executeSearch()
    }

    fun updateSearchFilters(
        sortBy: String? = null,
        filterType: String? = null,
        printType: String? = null
    ) {
        _searchState.value = _searchState.value.copy(
            sortBy = sortBy ?: _searchState.value.sortBy,
            filterType = filterType ?: _searchState.value.filterType,
            printType = printType ?: _searchState.value.printType
        )
        if (_searchState.value.query.isNotBlank() || _searchState.value.selectedGenre != null) {
            executeSearch()
        }
    }

    fun executeSearch(customQuery: String? = null) {
        val queryText = (customQuery ?: _searchState.value.query).trim()
        val genre = _searchState.value.selectedGenre

        val effectiveQuery = when {
            queryText.isNotBlank() && genre != null -> "$queryText subject:\"${genre.subjectParam}\""
            genre != null -> "subject:\"${genre.subjectParam}\""
            queryText.isNotBlank() -> queryText
            else -> return
        }

        searchJob?.cancel()
        _searchState.value = _searchState.value.copy(
            query = if (customQuery != null) customQuery else _searchState.value.query,
            isLoading = true,
            hasSearched = true,
            errorMessage = null
        )

        viewModelScope.launch {
            val filterParam = when (_searchState.value.filterType) {
                "ebooks" -> "ebooks"
                "free-ebooks" -> "free-ebooks"
                else -> null
            }

            val maxItems = settingsManager.maxResults.value
            val result = repository.searchBooks(
                query = effectiveQuery,
                maxResults = maxItems,
                orderBy = _searchState.value.sortBy,
                filter = filterParam,
                printType = _searchState.value.printType
            )

            val books = result.getOrDefault(emptyList()).ifEmpty {
                com.example.data.SampleBooksData.filterSampleBooks(
                    query = queryText,
                    genre = genre?.name,
                    sortBy = _searchState.value.sortBy
                ).ifEmpty { com.example.data.SampleBooksData.sampleBooks.take(8) }
            }

            _searchState.value = _searchState.value.copy(
                isLoading = false,
                books = books,
                errorMessage = null
            )
        }
    }

    fun clearSearch() {
        searchJob?.cancel()
        _searchState.value = SearchUiState()
    }

    fun loadBookDetail(bookId: String) {
        _detailState.value = DetailUiState(isLoading = true)
        viewModelScope.launch {
            val result = repository.getBookDetail(bookId)
            result.fold(
                onSuccess = { book ->
                    _detailState.value = DetailUiState(
                        book = book,
                        isLoading = false,
                        isFavorite = book.isFavorite
                    )
                },
                onFailure = { error ->
                    _detailState.value = DetailUiState(
                        isLoading = false,
                        errorMessage = error.localizedMessage ?: "Unable to load book details"
                    )
                }
            )
        }
    }

    fun toggleFavorite(book: Book) {
        viewModelScope.launch {
            val isNowFavorite = repository.toggleFavorite(book)
            val updatedBook = book.copy(isFavorite = isNowFavorite)

            if (_detailState.value.book?.id == book.id) {
                _detailState.value = _detailState.value.copy(
                    book = updatedBook,
                    isFavorite = isNowFavorite
                )
            }

            _searchState.value = _searchState.value.copy(
                books = _searchState.value.books.map { if (it.id == book.id) it.copy(isFavorite = isNowFavorite) else it }
            )

            _discoverState.value = _discoverState.value.copy(
                featuredBooks = _discoverState.value.featuredBooks.map { if (it.id == book.id) it.copy(isFavorite = isNowFavorite) else it },
                categoryBooks = _discoverState.value.categoryBooks.map { if (it.id == book.id) it.copy(isFavorite = isNowFavorite) else it }
            )

            val msg = if (isNowFavorite) "Saved to your Bookshelf ❤️" else "Removed from your Bookshelf"
            _userMessage.emit(msg)
        }
    }

    fun updateReadingStatus(bookId: String, status: ReadingStatus, note: String?) {
        viewModelScope.launch {
            repository.updateReadingStatus(bookId, status, note)
            if (_detailState.value.book?.id == bookId) {
                _detailState.value = _detailState.value.copy(
                    book = _detailState.value.book?.copy(readingStatus = status, userNote = note)
                )
            }
            _userMessage.emit("Updated reading status: ${status.label}")
        }
    }

    fun setFavoriteStatusFilter(status: ReadingStatus?) {
        favoriteStatusFilter.value = status
    }

    fun setFavoriteSearchFilter(query: String) {
        favoriteSearchFilter.value = query
    }
}

class BookViewModelFactory(
    private val repository: BookRepository,
    private val settingsManager: SettingsManager
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BookViewModel::class.java)) {
            return BookViewModel(repository, settingsManager) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}
