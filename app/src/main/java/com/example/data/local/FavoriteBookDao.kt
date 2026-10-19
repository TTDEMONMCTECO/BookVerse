package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteBookDao {

    @Query("SELECT * FROM favorite_books ORDER BY addedAt DESC")
    fun getAllFavorites(): Flow<List<FavoriteBookEntity>>

    @Query("SELECT * FROM favorite_books WHERE id = :bookId LIMIT 1")
    suspend fun getFavoriteById(bookId: String): FavoriteBookEntity?

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_books WHERE id = :bookId)")
    fun isFavorite(bookId: String): Flow<Boolean>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(book: FavoriteBookEntity)

    @Query("DELETE FROM favorite_books WHERE id = :bookId")
    suspend fun deleteFavoriteById(bookId: String)

    @Query("UPDATE favorite_books SET readingStatus = :status, userNote = :note WHERE id = :bookId")
    suspend fun updateReadingStatus(bookId: String, status: String, note: String?)
}
