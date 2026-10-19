package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.data.model.Book
import com.example.data.model.ReadingStatus

@Entity(tableName = "favorite_books")
data class FavoriteBookEntity(
    @PrimaryKey val id: String,
    val title: String,
    val subtitle: String? = null,
    val authors: String = "",
    val publisher: String? = null,
    val publishedDate: String? = null,
    val description: String? = null,
    val pageCount: Int? = null,
    val categories: String = "",
    val averageRating: Double? = null,
    val ratingsCount: Int? = null,
    val thumbnailUrl: String? = null,
    val previewLink: String? = null,
    val infoLink: String? = null,
    val readingStatus: String = ReadingStatus.WANT_TO_READ.name,
    val userNote: String? = null,
    val isEbook: Boolean = false,
    val addedAt: Long = System.currentTimeMillis()
)

fun FavoriteBookEntity.toBook(): Book {
    val authorList = if (authors.isBlank()) emptyList() else authors.split(",").map { it.trim() }
    val categoryList = if (categories.isBlank()) emptyList() else categories.split(",").map { it.trim() }

    return Book(
        id = id,
        title = title,
        subtitle = subtitle,
        authors = authorList,
        publisher = publisher,
        publishedDate = publishedDate,
        description = description,
        pageCount = pageCount,
        categories = categoryList,
        averageRating = averageRating,
        ratingsCount = ratingsCount,
        thumbnailUrl = thumbnailUrl,
        previewLink = previewLink,
        infoLink = infoLink,
        isFavorite = true,
        readingStatus = ReadingStatus.fromString(readingStatus),
        userNote = userNote,
        isEbook = isEbook
    )
}

fun Book.toEntity(): FavoriteBookEntity {
    return FavoriteBookEntity(
        id = id,
        title = title,
        subtitle = subtitle,
        authors = authors.joinToString(", "),
        publisher = publisher,
        publishedDate = publishedDate,
        description = description,
        pageCount = pageCount,
        categories = categories.joinToString(", "),
        averageRating = averageRating,
        ratingsCount = ratingsCount,
        thumbnailUrl = thumbnailUrl,
        previewLink = previewLink,
        infoLink = infoLink,
        readingStatus = readingStatus.name,
        userNote = userNote,
        isEbook = isEbook,
        addedAt = System.currentTimeMillis()
    )
}
