package com.example.pantallas

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DataBaseHelper(context: Context) : SQLiteOpenHelper (
    context,
    DATABASE_NAME,
    null,
    DATABASE_VERSION
){

    // DATOS DE LA BASE DE DATOS

    companion object{
        //INFORMACIÓN DE LA BD
        private const val DATABASE_NAME = "libros.db"
        private const val DATABASE_VERSION = 2

        // IMPORTACION DE LAS TABLAS EN LA BD

        private const val TABLE_USUARIOS = "usuarios"
        private const val COL_ID = "id"
        private const val COL_NOMBRE = "nombre"
        private const val COL_CORREO = "correo"
        private const val COL_PASSWORD = "password"

        private const val COL_TELEFONO = "telefono"

    }

    // METODO ONCREATE -- para crear la base de datos
    override fun onCreate(db: SQLiteDatabase?) {
        // CREAR LA TABLA
        val  crearTablaUsuarios = """
            CREATE TABLE $TABLE_USUARIOS(
            $COL_ID INTEGER PRIMARY KEY AUTOINCREMENT,
            $COL_NOMBRE TEXT NOT NULL,
            $COL_CORREO TEXT NOT NULL,
            $COL_PASSWORD TEXT NOT NULL,
            $COL_TELEFONO TEXT NOT NULL
            )
          """.trimIndent()

        // CREAR TABLA DE USUARIO
        db?.execSQL(crearTablaUsuarios)
    }

    // METODO ONUPGRADE
    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?. execSQL("DROP TABLE IF EXISTS $TABLE_USUARIOS")
        onCreate(db)
    }

    // METODO PARA REGISTRAR UN USUARIO
    fun registrarUsuario(nombre : String, correo : String, password : String, telefono : String) : Boolean {

        // CREAR VARIABLE PARA ESCRIBIR EN LA BD
        val db = writableDatabase

        // INSERTAR LOS DATOS
        val datos = ContentValues().apply {
            put(COL_NOMBRE, nombre)
            put(COL_CORREO, correo)
            put(COL_PASSWORD, password)
            put(COL_TELEFONO, telefono)
        }

        // EJECUTAR INSERCION DE DATOS
        val resultado = db.insert(TABLE_USUARIOS, null, datos)

        // CERRAR CONEXION DE LA DB
        db.close()

        // REGRESAR EL VALOR OBTENIDO DE LA INSERCION ( verdadero o falso )
        return resultado != -1L

    }

    // METODO PARA INISIAR SESION (CONSULTA)
    fun inisiarSesion(corre: String, password: String) : Boolean{

        // VARIABLE QUE LEE LA BASE DE DATOS
        val db = readableDatabase

        // CREAR CONSULTA
        val query_sql = """
            SELECT * FROM $TABLE_USUARIOS
            WHERE $COL_CORREO = ? AND $COL_PASSWORD = ?
        """.trimIndent()

        // CREAR CURSOR QUE PERMITA RECORRER TODOS LOS REGISTROS
        val cursor = db.rawQuery(
            query_sql, arrayOf(corre, password)
        )

        // VERIFICAR CUANTOS RESULTADOS ENCUENTRA EL CURSOR
        val usuarioEncontrado = cursor.count > 0
        cursor.close()
        db.close()

        return usuarioEncontrado
    }
}