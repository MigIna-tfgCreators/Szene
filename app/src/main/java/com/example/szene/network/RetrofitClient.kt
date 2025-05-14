package com.example.myapplication.network

import com.example.myapplication.core.API
import com.example.myapplication.core.API.BASE_URL
import com.example.szene.network.SearchService
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    val webService: WebService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(WebService::class.java)
    }

    val searchService: SearchService by lazy {
        Retrofit.Builder()
            .baseUrl(API.BASE_URL_BUSCADOR) // https://api.themoviedb.org/3/
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(SearchService::class.java)
    }

    }