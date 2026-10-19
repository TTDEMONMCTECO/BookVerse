package com.example

import com.example.data.local.FavoriteBookEntity
import com.example.data.local.toBook
import com.example.data.local.toEntity
import com.example.data.model.Book
import com.example.data.model.BookItemDto
import com.example.data.model.ImageLinksDto
import com.example.data.model.ReadingStatus
import com.example.data.model.VolumeInfoDto
import com.example.data.model.toDomain
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class ExampleUnitTest {

    @Test
    fun testBookDtoToDomainMapping() {
        val dto = BookItemDto(
            id = "test_123",
            volumeInfo = VolumeInfoDto(
                title = "Kotlin in Action",
                subtitle = "Second Edition",
                authors = listOf("Dmitry Jemerov", "Svetlana Isakova"),
                description = "Learn Kotlin programming",
                pageCount = 360,
                categories = listOf("Computers"),
                averageRating = 4.8,
                imageLinks = ImageLinksDto(
                    thumbnail = "http://books.google.com/thumbnail.jpg"
                )
            )
        )

        val book = dto.toDomain(isFavorite = true, readingStatus = ReadingStatus.READING)
        assertEquals("test_123", book.id)
        assertEquals("Kotlin in Action", book.title)
        assertEquals("Dmitry Jemerov, Svetlana Isakova", book.displayAuthors)
        assertEquals("https://books.google.com/thumbnail.jpg", book.thumbnailUrl)
        assertTrue(book.isFavorite)
        assertEquals(ReadingStatus.READING, book.readingStatus)
    }

    @Test
    fun testBookToEntityAndBack() {
        val original = Book(
            id = "ent_456",
            title = "Clean Architecture",
            authors = listOf("Robert C. Martin"),
            categories = listOf("Software Engineering"),
            averageRating = 4.7,
            isFavorite = true,
            readingStatus = ReadingStatus.COMPLETED,
            userNote = "Excellent read on system boundaries"
        )

        val entity: FavoriteBookEntity = original.toEntity()
        assertEquals("ent_456", entity.id)
        assertEquals("Robert C. Martin", entity.authors)
        assertEquals("Excellent read on system boundaries", entity.userNote)

        val converted = entity.toBook()
        assertEquals(original.id, converted.id)
        assertEquals(original.title, converted.title)
        assertEquals(original.displayAuthors, converted.displayAuthors)
        assertEquals(ReadingStatus.COMPLETED, converted.readingStatus)
        assertEquals(original.userNote, converted.userNote)
    }

    @Test
    fun testReadingStatusFromString() {
        assertEquals(ReadingStatus.WANT_TO_READ, ReadingStatus.fromString("WANT_TO_READ"))
        assertEquals(ReadingStatus.READING, ReadingStatus.fromString("Currently Reading"))
        assertEquals(ReadingStatus.COMPLETED, ReadingStatus.fromString("COMPLETED"))
        assertEquals(ReadingStatus.WANT_TO_READ, ReadingStatus.fromString("UNKNOWN_VALUE"))
    }
}
