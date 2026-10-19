package com.example.data.api

import com.example.data.model.BookItemDto
import com.example.data.model.BookSearchResponseDto
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface BookApiService {

    /**
     * Search books with parameter query and optional pagination, sorting, and filters.
     * Google Books API: https://www.googleapis.com/books/v1/volumes
     */
    @GET("volumes")
    suspend fun searchBooks(
        @Query("q") query: String,
        @Query("startIndex") startIndex: Int = 0,
        @Query("maxResults") maxResults: Int = 20,
        @Query("orderBy") orderBy: String = "relevance",
        @Query("filter") filter: String? = null,
        @Query("printType") printType: String = "books"
    ): BookSearchResponseDto

    /**
     * Get single book detail by volume id.
     * Google Books API: https://www.googleapis.com/books/v1/volumes/{id}
     */
    @GET("volumes/{id}")
    suspend fun getBookDetail(
        @Path("id") id: String
    ): BookItemDto
}

object ApiClient {
    private const val BASE_URL = "https://www.googleapis.com/books/v1/"

    private val moshi: Moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()

    private val okHttpClient: OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(15, TimeUnit.SECONDS)
        .readTimeout(15, TimeUnit.SECONDS)
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BASIC
        })
        .build()

    val apiService: BookApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(BookApiService::class.java)
    }
}
