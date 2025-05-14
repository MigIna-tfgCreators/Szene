package com.example.myapplication.network


import com.example.myapplication.network.response.PeliculasResponse
import com.example.szene.network.response.CreditosResponse
import com.example.szene.network.response.VideosResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Response
import retrofit2.http.Path

interface WebService {
    @GET("now_playing")
    suspend fun obtenerCartelera(
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "es-ES"
    ): Response<PeliculasResponse>

    @GET("popular")
    suspend fun obtenerPopulares(
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "es-ES"
    ): Response<PeliculasResponse>

    @GET("top_rated")
    suspend fun obtenerTop(
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "es-ES"
    ): Response<PeliculasResponse>

    @GET("{movie_id}/videos")
    suspend fun obtenerVideosPelicula(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "es-ES"
    ): Response<VideosResponse>

    @GET("search/movie")
    suspend fun searchMovies(
        @Query("query") query: String,
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "es-ES"
     ): Response<PeliculasResponse>


    }



