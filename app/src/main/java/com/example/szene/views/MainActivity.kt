package com.example.szene.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.example.szene.R
import com.example.szene.databinding.ActivityMainBinding
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.szene.models.PeliculaModel
import com.example.myapplication.viewmodels.PeliculasViewModel
import com.example.myapplication.views.AdapterPeliculas

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: PeliculasViewModel
    private lateinit var adapterPeliculas: AdapterPeliculas

    private var listaPeliculasOriginal: List<PeliculaModel> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[PeliculasViewModel::class.java]
        setupRecyclerView()

        viewModel.listaPeliculas.observe(this) { peliculas ->
            listaPeliculasOriginal = peliculas
            adapterPeliculas.actualizarPeliculas(listaPeliculasOriginal)
        }

        binding.etFilter.addTextChangedListener { userFilter ->
            val texto = userFilter.toString().trim()

            val listaFiltrada = if (texto.isEmpty()) {
                listaPeliculasOriginal
            } else {
                listaPeliculasOriginal.filter { pelicula ->
                    pelicula.nombrePelicula.lowercase().contains(texto.lowercase())
                }
            }

            adapterPeliculas.actualizarPeliculas(listaFiltrada)
        }

        binding.cvCartelera.setOnClickListener {
            viewModel.obtenerCartelera()
            cambiarColorBoton("car")
            cambiarTitulo("Cartelera")
        }

        binding.cvPopulares.setOnClickListener {
            viewModel.obtenerPopulares()
            cambiarColorBoton("pop")
            cambiarTitulo("Populares")
        }

        binding.cvTop.setOnClickListener {
            viewModel.obtenerTop()
            cambiarTitulo("Top")
        }

        viewModel.obtenerCartelera()
    }

    private fun cambiarTitulo(titulo: String) {
        binding.tvTitulo.text = titulo
    }

    private fun cambiarColorBoton(boton: String) {
        when (boton) {
            "car" -> {
                binding.cvCartelera.setCardBackgroundColor(resources.getColor(R.color.verde_200))
                binding.cvPopulares.setCardBackgroundColor(resources.getColor(R.color.azul_200))
            }
            "pop" -> {
                binding.cvCartelera.setCardBackgroundColor(resources.getColor(R.color.azul_200))
                binding.cvPopulares.setCardBackgroundColor(resources.getColor(R.color.verde_200))
            }
        }
    }

    private fun setupRecyclerView() {
        val layoutManager = GridLayoutManager(this, 3)
        binding.rvPeliculas.layoutManager = layoutManager
        adapterPeliculas = AdapterPeliculas(this, arrayListOf())
        binding.rvPeliculas.adapter = adapterPeliculas
    }
}
