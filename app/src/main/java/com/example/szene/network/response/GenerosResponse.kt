package com.example.szene.network.response

import com.google.gson.annotations.SerializedName

data class GenerosResponse(
    @SerializedName("genres") val genres: List<Generos>
)

data class Generos(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String
)
