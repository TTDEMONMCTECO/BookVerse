package com.example.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

// DTOs for Google Books API responses

@JsonClass(generateAdapter = true)
data class BookSearchResponseDto(
    @Json(name = "totalItems") val totalItems: Int? = null,
    @Json(name = "items") val items: List<BookItemDto>? = null
)

@JsonClass(generateAdapter = true)
data class BookItemDto(
    @Json(name = "id") val id: String,
    @Json(name = "volumeInfo") val volumeInfo: VolumeInfoDto? = null,
    @Json(name = "saleInfo") val saleInfo: SaleInfoDto? = null,
    @Json(name = "accessInfo") val accessInfo: AccessInfoDto? = null
)

@JsonClass(generateAdapter = true)
data class VolumeInfoDto(
    @Json(name = "title") val title: String? = null,
    @Json(name = "subtitle") val subtitle: String? = null,
    @Json(name = "authors") val authors: List<String>? = null,
    @Json(name = "publisher") val publisher: String? = null,
    @Json(name = "publishedDate") val publishedDate: String? = null,
    @Json(name = "description") val description: String? = null,
    @Json(name = "pageCount") val pageCount: Int? = null,
    @Json(name = "categories") val categories: List<String>? = null,
    @Json(name = "averageRating") val averageRating: Double? = null,
    @Json(name = "ratingsCount") val ratingsCount: Int? = null,
    @Json(name = "imageLinks") val imageLinks: ImageLinksDto? = null,
    @Json(name = "language") val language: String? = null,
    @Json(name = "previewLink") val previewLink: String? = null,
    @Json(name = "infoLink") val infoLink: String? = null
)

@JsonClass(generateAdapter = true)
data class ImageLinksDto(
    @Json(name = "smallThumbnail") val smallThumbnail: String? = null,
    @Json(name = "thumbnail") val thumbnail: String? = null,
    @Json(name = "small") val small: String? = null,
    @Json(name = "medium") val medium: String? = null,
    @Json(name = "large") val large: String? = null,
    @Json(name = "extraLarge") val extraLarge: String? = null
)

@JsonClass(generateAdapter = true)
data class SaleInfoDto(
    @Json(name = "saleability") val saleability: String? = null,
    @Json(name = "isEbook") val isEbook: Boolean? = null,
    @Json(name = "buyLink") val buyLink: String? = null
)

@JsonClass(generateAdapter = true)
data class AccessInfoDto(
    @Json(name = "webReaderLink") val webReaderLink: String? = null,
    @Json(name = "viewability") val viewability: String? = null
)

/**
 * Domain model used throughout the UI
 */
data class Book(
    val id: String,
    val title: String,
    val subtitle: String? = null,
    val authors: List<String> = emptyList(),
    val publisher: String? = null,
    val publishedDate: String? = null,
    val description: String? = null,
    val pageCount: Int? = null,
    val categories: List<String> = emptyList(),
    val averageRating: Double? = null,
    val ratingsCount: Int? = null,
    val thumbnailUrl: String? = null,
    val previewLink: String? = null,
    val infoLink: String? = null,
    val isFavorite: Boolean = false,
    val readingStatus: ReadingStatus = ReadingStatus.WANT_TO_READ,
    val userNote: String? = null,
    val isEbook: Boolean = false
) {
    val displayAuthors: String
        get() = if (authors.isEmpty()) "Unknown Author" else authors.joinToString(", ")

    val displayCategory: String
        get() = categories.firstOrNull() ?: "General"
}

enum class ReadingStatus(val label: String) {
    WANT_TO_READ("Want to Read"),
    READING("Currently Reading"),
    COMPLETED("Finished");

    companion object {
        fun fromString(value: String?): ReadingStatus {
            return entries.firstOrNull { it.name.equals(value, ignoreCase = true) || it.label.equals(value, ignoreCase = true) }
                ?: WANT_TO_READ
        }
    }
}

/**
 * Helper mapper from DTO to domain model
 */
fun BookItemDto.toDomain(isFavorite: Boolean = false, readingStatus: ReadingStatus = ReadingStatus.WANT_TO_READ, note: String? = null): Book {
    val info = volumeInfo
    // Secure HTTPS url conversion for thumbnail
    val rawImage = info?.imageLinks?.thumbnail 
        ?: info?.imageLinks?.smallThumbnail 
        ?: info?.imageLinks?.medium
    val secureThumbnail = rawImage?.replace("http://", "https://")

    return Book(
        id = id,
        title = info?.title ?: "Untitled Book",
        subtitle = info?.subtitle,
        authors = info?.authors ?: emptyList(),
        publisher = info?.publisher,
        publishedDate = info?.publishedDate,
        description = info?.description,
        pageCount = info?.pageCount,
        categories = info?.categories ?: emptyList(),
        averageRating = info?.averageRating,
        ratingsCount = info?.ratingsCount,
        thumbnailUrl = secureThumbnail,
        previewLink = info?.previewLink?.replace("http://", "https://"),
        infoLink = info?.infoLink?.replace("http://", "https://"),
        isFavorite = isFavorite,
        readingStatus = readingStatus,
        userNote = note,
        isEbook = saleInfo?.isEbook == true
    )
}
