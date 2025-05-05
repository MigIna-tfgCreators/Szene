package com.example.szene.views

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.myapplication.core.API
import com.example.myapplication.viewmodels.PeliculasViewModel
import com.example.szene.R
import com.example.szene.models.PeliculasDetalles
import com.example.szene.network.PeliculaService
import com.example.szene.network.response.CreditosResponse
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DetallesActivity : AppCompatActivity() {

    private lateinit var viewModel: PeliculasViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle)

        val idPelicula = intent.getIntExtra("id_pelicula", -1)
        Toast.makeText(this, "ID recibido: $idPelicula", Toast.LENGTH_SHORT).show()

        viewModel = ViewModelProvider(this)[PeliculasViewModel::class.java]

        val youTubePlayerView = findViewById<YouTubePlayerView>(R.id.youtubePlayerView)
        lifecycle.addObserver(youTubePlayerView)

        viewModel.trailerUrl.observe(this) { url ->
            if (url.startsWith("http")) {
                val videoId = url.substringAfter("v=")
                youTubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                    override fun onReady(youTubePlayer: YouTubePlayer) {
                        youTubePlayer.cueVideo(videoId, 0f)
                    }
                })
            }
        }


        // ✅ Pedimos el tráiler desde el ViewModel
        if (idPelicula != -1) {
            obtenerDetalles(idPelicula)
            viewModel.obtenerTrailerYouTube(idPelicula, API.API_KEY)
        }

        findViewById<Button>(R.id.botonAtras).setOnClickListener {
            finish()
        }
    }

    private fun obtenerDetalles(id: Int) {
        val retrofit = Retrofit.Builder()
            .baseUrl(API.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(PeliculaService::class.java)
        val call = service.obtenerDetallesPelicula(id, API.API_KEY)

        call.enqueue(object : Callback<PeliculasDetalles> {
            override fun onResponse(
                call: Call<PeliculasDetalles>,
                response: Response<PeliculasDetalles>
            ) {
                if (response.isSuccessful) {
                    val detalles = response.body()
                    detalles?.let {
                        mostrarDetalles(it)
                        obtenerCreditos(it.id)
                    }
                    Log.d("API_DEBUG", "Datos recibidos: $detalles")
                } else {
                    Log.e("API_DEBUG", "Error en respuesta: ${response.code()} - ${response.message()}")
                    Toast.makeText(this@DetallesActivity, "Error: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<PeliculasDetalles>, t: Throwable) {
                Toast.makeText(this@DetallesActivity, "Error al cargar detalles", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun mostrarDetalles(detalles: PeliculasDetalles) {
        val tvTitulo = findViewById<TextView>(R.id.tvTituloDetalle)
        val ivPoster = findViewById<ImageView>(R.id.ivPosterDetalle)
        val tvDescripcion = findViewById<TextView>(R.id.tvDescripcionDetalle)
        val tvGenero = findViewById<TextView>(R.id.tvGeneroDetalle)

        tvTitulo.text = detalles.title
        tvDescripcion.text = detalles.overview

        val nombresGeneros = detalles.genres.joinToString(", ") { it.name }
        tvGenero.text = "Género(s): $nombresGeneros"

        Glide.with(this)
            .load("${API.BASE_URL_IMAGEN}${detalles.posterPath}")
            .into(ivPoster)
    }

    private fun obtenerCreditos(id: Int) {
        val retrofit = Retrofit.Builder()
            .baseUrl(API.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(PeliculaService::class.java)
        val call = service.obtenerCreditos(id, API.API_KEY, "es-ES")

        call.enqueue(object : Callback<CreditosResponse> {
            override fun onResponse(call: Call<CreditosResponse>, response: Response<CreditosResponse>) {
                if (response.isSuccessful) {
                    val creditos = response.body()
                    creditos?.let {
                        mostrarCreditos(it)
                    }
                } else {
                    Log.e("API_CREDITOS", "Error ${response.code()}")
                }
            }

            override fun onFailure(call: Call<CreditosResponse>, t: Throwable) {
                Log.e("API_CREDITOS", "Fallo: ${t.message}", t)
            }
        })
    }

    private fun mostrarCreditos(creditos: CreditosResponse) {
        val tvReparto = findViewById<TextView>(R.id.tvReparto)
        val tvDirector = findViewById<TextView>(R.id.tvDirector)
        val tvGuionista = findViewById<TextView>(R.id.tvGuionista)

        val reparto = creditos.cast.take(3).joinToString("\n") {
            "${it.nombre} como ${it.personaje}"
        }
        tvReparto.text = reparto

        val director = creditos.crew.firstOrNull { it.trabajo == "Director" }
        val guionista = creditos.crew.firstOrNull { it.trabajo == "Writer" || it.trabajo == "Screenplay" }

        director?.let {
            tvDirector.text = "Director: ${it.nombre}"
        }

        guionista?.let {
            tvGuionista.text = "Guionista: ${it.nombre}"
        }
    }
}









