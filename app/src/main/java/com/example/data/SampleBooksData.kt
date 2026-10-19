package com.example.data

import com.example.data.model.Book
import com.example.data.model.ReadingStatus

object SampleBooksData {

    val sampleBooks = listOf(
        Book(
            id = "sample_dune",
            title = "Dune",
            subtitle = "The Epic Chronicle of Arrakis",
            authors = listOf("Frank Herbert"),
            publisher = "Chilton Books",
            publishedDate = "1965",
            description = "Set on the desert planet Arrakis, Dune is the story of the boy Paul Atreides, heir to a noble family tasked with ruling an inhospitable world where the only thing of value is the 'spice' melange, a drug capable of extending life and enhancing consciousness.",
            pageCount = 688,
            categories = listOf("Fiction & Fantasy", "Science Fiction"),
            averageRating = 4.8,
            ratingsCount = 12450,
            thumbnailUrl = "https://images.unsplash.com/photo-1544716278-ca5e3f4abd8c?w=400&q=80",
            previewLink = "https://www.google.com/books/edition/Dune/B1hGBAAAQBAJ",
            infoLink = "https://books.google.com/books?id=B1hGBAAAQBAJ",
            isFavorite = false,
            readingStatus = ReadingStatus.WANT_TO_READ,
            isEbook = true
        ),
        Book(
            id = "sample_atomic_habits",
            title = "Atomic Habits",
            subtitle = "An Easy & Proven Way to Build Good Habits & Break Bad Ones",
            authors = listOf("James Clear"),
            publisher = "Avery / Penguin",
            publishedDate = "2018-10-16",
            description = "No matter your goals, Atomic Habits offers a proven framework for improving every day. James Clear, one of the world's leading experts on habit formation, reveals practical strategies that will teach you exactly how to form good habits, break bad ones, and master the tiny behaviors that lead to remarkable results.",
            pageCount = 320,
            categories = listOf("Psychology & Mind", "Self-Help"),
            averageRating = 4.9,
            ratingsCount = 28900,
            thumbnailUrl = "https://images.unsplash.com/photo-1512820790803-83ca734da794?w=400&q=80",
            previewLink = "https://www.google.com/books/edition/Atomic_Habits/fFCjDQAAQBAJ",
            infoLink = "https://books.google.com/books?id=fFCjDQAAQBAJ",
            isFavorite = false,
            readingStatus = ReadingStatus.READING,
            isEbook = true
        ),
        Book(
            id = "sample_clean_code",
            title = "Clean Code",
            subtitle = "A Handbook of Agile Software Craftsmanship",
            authors = listOf("Robert C. Martin"),
            publisher = "Prentice Hall",
            publishedDate = "2008-08-01",
            description = "Even bad code can function. But if code isn't clean, it can bring a development organization to its knees. Every year, countless hours and significant resources are lost because of poorly written code. Clean Code offers pragmatic advice for crafting elegant, readable, and maintainable software.",
            pageCount = 464,
            categories = listOf("Tech & Coding", "Computer Science"),
            averageRating = 4.7,
            ratingsCount = 8900,
            thumbnailUrl = "https://images.unsplash.com/photo-1516259762381-22954d7d3ad2?w=400&q=80",
            previewLink = "https://www.google.com/books/edition/Clean_Code/hjEFCAAAQBAJ",
            infoLink = "https://books.google.com/books?id=hjEFCAAAQBAJ",
            isFavorite = false,
            readingStatus = ReadingStatus.WANT_TO_READ,
            isEbook = true
        ),
        Book(
            id = "sample_sapiens",
            title = "Sapiens",
            subtitle = "A Brief History of Humankind",
            authors = listOf("Yuval Noah Harari"),
            publisher = "Harper",
            publishedDate = "2015-02-10",
            description = "One hundred thousand years ago, at least six human species inhabited the earth. Today there is just one: Homo sapiens. How did our species succeed in the battle for dominance? Sapiens spans the whole of human history, from the very first humans to walk the earth to the radical revolutions of the cognitive, agricultural, and scientific eras.",
            pageCount = 464,
            categories = listOf("History & Bio", "Anthropology"),
            averageRating = 4.8,
            ratingsCount = 34200,
            thumbnailUrl = "https://images.unsplash.com/photo-1461360370896-922624d12aa1?w=400&q=80",
            previewLink = "https://www.google.com/books/edition/Sapiens/FmyBAwAAQBAJ",
            infoLink = "https://books.google.com/books?id=FmyBAwAAQBAJ",
            isFavorite = false,
            readingStatus = ReadingStatus.COMPLETED,
            isEbook = true
        ),
        Book(
            id = "sample_thinking_fast_slow",
            title = "Thinking, Fast and Slow",
            subtitle = "Two Systems that Drive Human Thought",
            authors = listOf("Daniel Kahneman"),
            publisher = "Farrar, Straus and Giroux",
            publishedDate = "2011-10-25",
            description = "The phenomenal New York Times bestseller by Nobel Memorial Prize in Economic Sciences laureate Daniel Kahneman takes us on a groundbreaking tour of the mind and explains the two systems that drive the way we think: System 1 is fast, intuitive, and emotional; System 2 is slower, more deliberative, and more logical.",
            pageCount = 512,
            categories = listOf("Psychology & Mind", "Cognitive Science"),
            averageRating = 4.6,
            ratingsCount = 19500,
            thumbnailUrl = "https://images.unsplash.com/photo-1456513080510-7bf3a84b82f8?w=400&q=80",
            previewLink = "https://www.google.com/books/edition/Thinking_Fast_and_Slow/ZuKTvERuNWUC",
            infoLink = "https://books.google.com/books?id=ZuKTvERuNWUC",
            isFavorite = false,
            readingStatus = ReadingStatus.WANT_TO_READ,
            isEbook = true
        ),
        Book(
            id = "sample_design_everyday",
            title = "The Design of Everyday Things",
            subtitle = "Revised and Expanded Edition",
            authors = listOf("Don Norman"),
            publisher = "Basic Books",
            publishedDate = "2013-11-05",
            description = "Even the smartest among us can feel inept as we try to figure out which light switch or oven burner to turn on, or whether to push, pull, or slide a door. The Design of Everyday Things is a powerful primer on how and why some products satisfy customers while others only frustrate them.",
            pageCount = 368,
            categories = listOf("Design & Art", "Architecture & UX"),
            averageRating = 4.7,
            ratingsCount = 7600,
            thumbnailUrl = "https://images.unsplash.com/photo-1507842229451-79b1be886a20?w=400&q=80",
            previewLink = "https://www.google.com/books/edition/The_Design_of_Everyday_Things/nl1LBAAAQBAJ",
            infoLink = "https://books.google.com/books?id=nl1LBAAAQBAJ",
            isFavorite = false,
            readingStatus = ReadingStatus.WANT_TO_READ,
            isEbook = true
        ),
        Book(
            id = "sample_astrophysics_hurry",
            title = "Astrophysics for People in a Hurry",
            subtitle = "Quick Wisdom on the Cosmos",
            authors = listOf("Neil deGrasse Tyson"),
            publisher = "W. W. Norton & Company",
            publishedDate = "2017-05-02",
            description = "What is the nature of space and time? How do we fit within the universe? Neil deGrasse Tyson brings the cosmic secrets of quantum mechanics, black holes, and the Big Bang into mind-expanding, bite-sized chapters that can be read anywhere, anytime.",
            pageCount = 224,
            categories = listOf("Science & Space", "Astrophysics"),
            averageRating = 4.7,
            ratingsCount = 14300,
            thumbnailUrl = "https://images.unsplash.com/photo-1506703719100-a0f3a48c0f86?w=400&q=80",
            previewLink = "https://www.google.com/books/edition/Astrophysics_for_People_in_a_Hurry/Wj3dDAAAQBAJ",
            infoLink = "https://books.google.com/books?id=Wj3dDAAAQBAJ",
            isFavorite = false,
            readingStatus = ReadingStatus.WANT_TO_READ,
            isEbook = true
        ),
        Book(
            id = "sample_lean_startup",
            title = "The Lean Startup",
            subtitle = "How Today's Entrepreneurs Use Continuous Innovation to Create Radically Successful Businesses",
            authors = listOf("Eric Ries"),
            publisher = "Crown Business",
            publishedDate = "2011-09-13",
            description = "Most startups fail. But many of those failures are preventable. The Lean Startup is a new approach being adopted across the globe, changing the way companies are built and new products are launched. Rather than executing vanity plans, entrepreneurs learn how to validate ideas with real feedback.",
            pageCount = 336,
            categories = listOf("Business & Startups", "Innovation"),
            averageRating = 4.6,
            ratingsCount = 16800,
            thumbnailUrl = "https://images.unsplash.com/photo-1519389950473-47ba0277781c?w=400&q=80",
            previewLink = "https://www.google.com/books/edition/The_Lean_Startup/rGwhCwAAQBAJ",
            infoLink = "https://books.google.com/books?id=rGwhCwAAQBAJ",
            isFavorite = false,
            readingStatus = ReadingStatus.WANT_TO_READ,
            isEbook = true
        ),
        Book(
            id = "sample_meditations",
            title = "Meditations",
            subtitle = "Thoughts of a Roman Emperor",
            authors = listOf("Marcus Aurelius"),
            publisher = "Penguin Classics",
            publishedDate = "180",
            description = "A series of personal writings by Marcus Aurelius, Roman Emperor from 161 to 180 AD, recording his private notes to himself and ideas on Stoic philosophy. It remains one of the greatest psychological and philosophical guides on resilience, duty, and tranquility ever written.",
            pageCount = 256,
            categories = listOf("Philosophy", "Stoicism"),
            averageRating = 4.8,
            ratingsCount = 22100,
            thumbnailUrl = "https://images.unsplash.com/photo-1506880018603-83d5b814b5a6?w=400&q=80",
            previewLink = "https://www.google.com/books/edition/Meditations/xUgtDAAAQBAJ",
            infoLink = "https://books.google.com/books?id=xUgtDAAAQBAJ",
            isFavorite = false,
            readingStatus = ReadingStatus.COMPLETED,
            isEbook = true
        ),
        Book(
            id = "sample_dragon_tattoo",
            title = "The Girl with the Dragon Tattoo",
            subtitle = "Millennium Series #1",
            authors = listOf("Stieg Larsson"),
            publisher = "Norstedts Förlag",
            publishedDate = "2005-08-01",
            description = "Murder mystery, family saga, love story, and financial intrigue combine into one satisfyingly complex and entertaining thriller as journalist Mikael Blomkvist and genius hacker Lisbeth Salander uncover dark secrets spanning decades.",
            pageCount = 672,
            categories = listOf("Fiction & Fantasy", "Mystery & Crime"),
            averageRating = 4.7,
            ratingsCount = 31200,
            thumbnailUrl = "https://images.unsplash.com/photo-1509198397868-475647b2a1e5?w=400&q=80",
            previewLink = "https://www.google.com/books/edition/The_Girl_with_the_Dragon_Tattoo/i78L36u26yYC",
            infoLink = "https://books.google.com/books?id=i78L36u26yYC",
            isFavorite = false,
            readingStatus = ReadingStatus.WANT_TO_READ,
            isEbook = true
        ),
        Book(
            id = "sample_1984",
            title = "1984",
            subtitle = "A Dystopian Masterpiece",
            authors = listOf("George Orwell"),
            publisher = "Secker & Warburg",
            publishedDate = "1949-06-08",
            description = "Winston Smith toils for the Ministry of Truth in the totalitarian superstate of Oceania. Under the watchful eye of Big Brother and the Thought Police, Winston dares to express forbidden individuality and love in a world of perpetual war and surveillance.",
            pageCount = 328,
            categories = listOf("Fiction & Fantasy", "Classic Dystopian"),
            averageRating = 4.9,
            ratingsCount = 45000,
            thumbnailUrl = "https://images.unsplash.com/photo-1543002588-bfa74002ed7e?w=400&q=80",
            previewLink = "https://www.google.com/books/edition/1984/kotPYEqx7kMC",
            infoLink = "https://books.google.com/books?id=kotPYEqx7kMC",
            isFavorite = false,
            readingStatus = ReadingStatus.WANT_TO_READ,
            isEbook = true
        ),
        Book(
            id = "sample_steve_jobs",
            title = "Steve Jobs",
            subtitle = "The Exclusive Biography",
            authors = listOf("Walter Isaacson"),
            publisher = "Simon & Schuster",
            publishedDate = "2011-10-24",
            description = "Based on more than forty interviews with Steve Jobs conducted over two years—as well as interviews with more than a hundred family members, friends, adversaries, competitors, and colleagues—Walter Isaacson has written a riveting story of the roller-coaster life and searingly intense personality of a creative entrepreneur whose passion for perfection revolutionized six industries.",
            pageCount = 656,
            categories = listOf("History & Bio", "Biography"),
            averageRating = 4.8,
            ratingsCount = 27800,
            thumbnailUrl = "https://images.unsplash.com/photo-1534447677768-be436bb09401?w=400&q=80",
            previewLink = "https://www.google.com/books/edition/Steve_Jobs/8U2oAAAAQBAJ",
            infoLink = "https://books.google.com/books?id=8U2oAAAAQBAJ",
            isFavorite = false,
            readingStatus = ReadingStatus.WANT_TO_READ,
            isEbook = true
        ),
        Book(
            id = "sample_zero_to_one",
            title = "Zero to One",
            subtitle = "Notes on Startups, or How to Build the Future",
            authors = listOf("Peter Thiel", "Blake Masters"),
            publisher = "Crown Business",
            publishedDate = "2014-09-16",
            description = "If you want to build a better future, you must believe in secrets. The great secret of our time is that there are still uncharted frontiers to explore and new inventions to create. In Zero to One, legendary entrepreneur and investor Peter Thiel shows how we can find singular ways to create those new things.",
            pageCount = 224,
            categories = listOf("Business & Startups", "Technology"),
            averageRating = 4.7,
            ratingsCount = 19100,
            thumbnailUrl = "https://images.unsplash.com/photo-1481627834876-b7833e8f5570?w=400&q=80",
            previewLink = "https://www.google.com/books/edition/Zero_to_One/3U2oAAAAQBAJ",
            infoLink = "https://books.google.com/books?id=3U2oAAAAQBAJ",
            isFavorite = false,
            readingStatus = ReadingStatus.WANT_TO_READ,
            isEbook = true
        ),
        Book(
            id = "sample_android_compose",
            title = "Jetpack Compose Internals",
            subtitle = "Architecture, Recomposition, and Modern UI Patterns",
            authors = listOf("Jorge Castillo"),
            publisher = "Android Dev Press",
            publishedDate = "2022-04-10",
            description = "A deep dive into how Jetpack Compose works under the hood. Learn compiler transformations, snapshot state systems, slot table data structures, layout phases, and performance optimization techniques for production Android applications.",
            pageCount = 380,
            categories = listOf("Tech & Coding", "Mobile Engineering"),
            averageRating = 4.9,
            ratingsCount = 3400,
            thumbnailUrl = "https://images.unsplash.com/photo-1555066931-4365d14bab8c?w=400&q=80",
            previewLink = "https://developer.android.com/jetpack/compose",
            infoLink = "https://developer.android.com/jetpack/compose",
            isFavorite = false,
            readingStatus = ReadingStatus.READING,
            isEbook = true
        ),
        Book(
            id = "sample_sherlock_holmes",
            title = "The Hound of the Baskervilles",
            subtitle = "A Sherlock Holmes Mystery",
            authors = listOf("Arthur Conan Doyle"),
            publisher = "George Newnes",
            publishedDate = "1902-03-25",
            description = "Sherlock Holmes and Dr. Watson investigate the legend of a diabolical hound that stalks the moorland estate of Dartmoor, threatening the life of Sir Henry Baskerville.",
            pageCount = 256,
            categories = listOf("Fiction & Fantasy", "Mystery & Crime"),
            averageRating = 4.7,
            ratingsCount = 18700,
            thumbnailUrl = "https://images.unsplash.com/photo-1516979187457-637abb4f9353?w=400&q=80",
            previewLink = "https://www.google.com/books/edition/The_Hound_of_the_Baskervilles/P_dCAQAAMAAJ",
            infoLink = "https://books.google.com/books?id=P_dCAQAAMAAJ",
            isFavorite = false,
            readingStatus = ReadingStatus.WANT_TO_READ,
            isEbook = true
        )
    )

    fun getById(id: String): Book? {
        return sampleBooks.firstOrNull { it.id == id }
    }

    fun filterSampleBooks(
        query: String = "",
        genre: String? = null,
        sortBy: String = "relevance"
    ): List<Book> {
        var filtered = sampleBooks.filter { book ->
            val matchesGenre = genre == null || 
                book.categories.any { it.contains(genre, ignoreCase = true) } ||
                book.title.contains(genre, ignoreCase = true)

            val matchesQuery = query.isBlank() ||
                book.title.contains(query, ignoreCase = true) ||
                book.displayAuthors.contains(query, ignoreCase = true) ||
                book.description?.contains(query, ignoreCase = true) == true ||
                book.categories.any { it.contains(query, ignoreCase = true) }

            matchesGenre && matchesQuery
        }

        if (sortBy == "newest") {
            filtered = filtered.sortedByDescending { it.publishedDate }
        }

        return filtered
    }
}
