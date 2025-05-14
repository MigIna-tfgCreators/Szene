package com.example.szene.network

import com.example.myapplication.network.response.PeliculasResponse
import com.example.szene.network.response.GenerosResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchService {
    @GET("search/movie")
    suspend fun searchMovies(
        @Query("query") query: String,
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "es-ES"
    ): Response<PeliculasResponse>


    @GET("genre/movie/list")
    suspend fun getGeneros(
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "es-ES"
    ): Response<GenerosResponse>
}
