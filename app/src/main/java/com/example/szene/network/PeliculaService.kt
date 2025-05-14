package com.example.szene.network

import com.example.myapplication.network.WebService
import com.example.szene.models.PeliculasDetalles
import com.example.szene.network.response.CreditosResponse
import com.example.szene.network.response.VideosResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PeliculaService {
    @GET("{movie_id}")
    fun obtenerDetallesPelicula(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "es-ES"
    ): Call<PeliculasDetalles>

    @GET("{movie_id}/credits")
    fun obtenerCreditos(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "es-ES"
    ): Call<CreditosResponse>

}





