package com.example.myapplication.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.core.API
import com.example.szene.models.PeliculaModel
import com.example.myapplication.network.RetrofitClient
import com.example.szene.network.PeliculaService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PeliculasViewModel: ViewModel() {

    private var _listaPelicuas =
        MutableLiveData<List<PeliculaModel>>() //Permite modificar la informacion de la lista
    val listaPeliculas: LiveData<List<PeliculaModel>> =
        _listaPelicuas //Se va a cosumir en las listas y no se puede modificar

    private val _trailerUrl = MutableLiveData<String>()
    val trailerUrl: LiveData<String> get() = _trailerUrl

    fun obtenerCartelera() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = RetrofitClient.webService.obtenerCartelera(API.API_KEY)
            withContext(Dispatchers.Main) {
                _listaPelicuas.value =
                    response.body()!!.resultados.sortedByDescending { it.votoPromedio }
            }
        }
    }

    fun obtenerPopulares() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = RetrofitClient.webService.obtenerPopulares(API.API_KEY)
            withContext(Dispatchers.Main) {
                _listaPelicuas.value =
                    response.body()!!.resultados.sortedByDescending { it.votoPromedio }
            }
        }
    }

    fun obtenerTop() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = RetrofitClient.webService.obtenerTop(API.API_KEY)
            withContext(Dispatchers.Main) {
                _listaPelicuas.value =
                    response.body()!!.resultados.sortedByDescending { it.votoPromedio }
            }
        }
    }

    fun obtenerTrailerYouTube(idPelicula: Int, apiKey: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = RetrofitClient.webService.obtenerVideosPelicula(idPelicula, apiKey)

                if (response.isSuccessful) {
                    val body = response.body()
                    val trailer = body?.resultados?.firstOrNull {
                        it.sitio == "YouTube" && it.tipo == "Trailer"
                    }

                    withContext(Dispatchers.Main) {
                        _trailerUrl.value = trailer?.let {
                            "https://www.youtube.com/watch?v=${it.clave}"
                        } ?: "Trailer no disponible"
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        _trailerUrl.value = "Error: ${response.code()}"
                    }
                }

            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _trailerUrl.value = "Error al obtener el trailer"
                }
            }
        }
    }
}
