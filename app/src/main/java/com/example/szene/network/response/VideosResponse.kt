package com.example.szene.network.response
import com.google.gson.annotations.SerializedName

data class VideosResponse(
    @SerializedName("results")
    val resultados: List<Video>
)

data class Video(
    @SerializedName("id") val id: String,
    @SerializedName("key") val clave: String, // YouTube video key
    @SerializedName("name") val nombre: String,
    @SerializedName("site") val sitio: String, // YouTube, Vimeo, etc.
    @SerializedName("type") val tipo: String   // Trailer, Teaser, etc.
)
