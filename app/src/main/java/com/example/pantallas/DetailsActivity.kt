
package com.example.pantallas

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DetailsActivity : AppCompatActivity() {

    private lateinit var tvResultado: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        tvResultado = findViewById(R.id.tvResultado)

        mostrarInformacionLibro()
    }

    private fun mostrarInformacionLibro() {

        val idLibro = intent.getStringExtra("ID_LIBRO")
        val nombreLibro = intent.getStringExtra("NOMBRE_LIBRO")
        val autor = intent.getStringExtra("AUTOR_LIBRO")
        val anio = intent.getIntExtra("ANIO_LIBRO", 0)

        tvResultado.text =
            "Nombre del libro: ${nombreLibro ?: "Sin título"}"
    }
}
