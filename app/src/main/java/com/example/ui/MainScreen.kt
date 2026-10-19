package com.example.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.Explore
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.data.api.ApiClient
import com.example.data.local.BookDatabase
import com.example.data.local.SettingsManager
import com.example.data.repository.BookRepository
import com.example.ui.screens.BookDetailScreen
import com.example.ui.screens.DiscoverScreen
import com.example.ui.screens.FavoritesScreen
import com.example.ui.screens.SearchScreen
import com.example.ui.screens.SettingsScreen
import com.example.ui.viewmodel.BookViewModel
import com.example.ui.viewmodel.BookViewModelFactory
import kotlinx.coroutines.flow.collectLatest

enum class MainTab(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val testTag: String
) {
    DISCOVER("Discover", Icons.Filled.Explore, Icons.Outlined.Explore, "nav_discover"),
    SEARCH("Search", Icons.Filled.Search, Icons.Outlined.Search, "nav_search"),
    FAVORITES("Bookshelf", Icons.Filled.Bookmark, Icons.Outlined.BookmarkBorder, "nav_favorites"),
    SETTINGS("Settings", Icons.Filled.Settings, Icons.Outlined.Settings, "nav_settings")
}

@Composable
fun MainScreen(
    viewModel: BookViewModel,
    modifier: Modifier = Modifier
) {
    var currentTab by rememberSaveable { mutableStateOf(MainTab.DISCOVER) }
    var selectedBookId by rememberSaveable { mutableStateOf<String?>(null) }
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(viewModel) {
        viewModel.userMessage.collectLatest { message ->
            snackbarHostState.showSnackbar(message)
        }
    }

    if (selectedBookId != null) {
        BookDetailScreen(
            bookId = selectedBookId!!,
            viewModel = viewModel,
            onBack = { selectedBookId = null }
        )
    } else {
        Scaffold(
            bottomBar = {
                NavigationBar(
                    windowInsets = WindowInsets.navigationBars,
                    containerColor = MaterialTheme.colorScheme.surface,
                    contentColor = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.testTag("main_bottom_nav")
                ) {
                    MainTab.entries.forEach { tab ->
                        val isSelected = currentTab == tab
                        NavigationBarItem(
                            selected = isSelected,
                            onClick = { currentTab = tab },
                            icon = {
                                Icon(
                                    imageVector = if (isSelected) tab.selectedIcon else tab.unselectedIcon,
                                    contentDescription = tab.title
                                )
                            },
                            label = {
                                Text(tab.title, style = MaterialTheme.typography.labelSmall)
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = MaterialTheme.colorScheme.primary,
                                selectedTextColor = MaterialTheme.colorScheme.primary,
                                indicatorColor = MaterialTheme.colorScheme.primaryContainer,
                                unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                            ),
                            modifier = Modifier.testTag(tab.testTag)
                        )
                    }
                }
            },
            snackbarHost = {
                SnackbarHost(hostState = snackbarHostState)
            },
            containerColor = MaterialTheme.colorScheme.background,
            modifier = modifier.fillMaxSize()
        ) { innerPadding ->
            AnimatedContent(
                targetState = currentTab,
                transitionSpec = {
                    (fadeIn() + slideInHorizontally { width -> if (targetState.ordinal > initialState.ordinal) width else -width })
                        .togetherWith(fadeOut() + slideOutHorizontally { width -> if (targetState.ordinal > initialState.ordinal) -width else width })
                },
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                label = "tabTransition"
            ) { targetTab ->
                when (targetTab) {
                    MainTab.DISCOVER -> {
                        DiscoverScreen(
                            viewModel = viewModel,
                            onBookClick = { bookId -> selectedBookId = bookId },
                            onNavigateToSearch = { query ->
                                currentTab = MainTab.SEARCH
                                if (!query.isNullOrBlank()) {
                                    viewModel.executeSearch(query)
                                }
                            }
                        )
                    }

                    MainTab.SEARCH -> {
                        SearchScreen(
                            viewModel = viewModel,
                            onBookClick = { bookId -> selectedBookId = bookId }
                        )
                    }

                    MainTab.FAVORITES -> {
                        FavoritesScreen(
                            viewModel = viewModel,
                            onBookClick = { bookId -> selectedBookId = bookId },
                            onNavigateToDiscover = { currentTab = MainTab.DISCOVER }
                        )
                    }

                    MainTab.SETTINGS -> {
                        SettingsScreen(
                            viewModel = viewModel
                        )
                    }
                }
            }
        }
    }
}
