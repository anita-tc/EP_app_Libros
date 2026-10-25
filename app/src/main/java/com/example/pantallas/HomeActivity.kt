
package com.example.pantallas

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.pantallas.model.LibroResponse
import com.example.pantallas.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeActivity : AppCompatActivity() {

    private lateinit var tvResultado: TextView
    private lateinit var imgLibro: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        tvResultado = findViewById(R.id.tvResultado)
        imgLibro = findViewById(R.id.imgLibro)

        // Llamar al me para obtener libros
        obtenerLibros()
    }

    //  PARA OBTENER INFORMACIÓN DE LOS LIBROS
    private fun obtenerLibros() {

        RetrofitClient.api.obtenerLibros(
            busqueda = "Harry Potter",
            limite = 10
        ).enqueue(object : Callback<LibroResponse> {

            // MÉTODO CUANDO LA RESPUESTA ES CORRECTA
            override fun onResponse(
                call: Call<LibroResponse>,
                response: Response<LibroResponse>
            ) {

                val libros = response.body()?.docs

                if (response.isSuccessful && !libros.isNullOrEmpty()) {

                    // Obtener el primer libro encontrado
                    val libro = libros[0]

                    tvResultado.text =
                        "Título: ${libro.title ?: "Sin título"}\n\n" +
                                "Autor: ${libro.authorName?.joinToString(", ") ?: "Autor desconocido"}\n\n" +
                                "Año de publicación: ${libro.firstPublishYear ?: "No disponible"}"

                    // Mostrar la portada del libro
                    Glide.with(this@HomeActivity)
                        .load(libro.obtenerImagenGrande())
                        .into(imgLibro)

                } else {
                    Toast.makeText(
                        this@HomeActivity,
                        "No se encontraron libros",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            // MÉTODO CUANDO FALLA LA CONEXIÓN
            override fun onFailure(
                call: Call<LibroResponse>,
                t: Throwable
            ) {
                Toast.makeText(
                    this@HomeActivity,
                    "Error al conectar con el API: ${t.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }
}
