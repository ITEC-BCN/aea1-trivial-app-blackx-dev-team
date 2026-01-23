package com.example.trivialapp_base.model

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException

data class Pregunta(
    val pregunta: String,
    val categoria: String,
    val dificultad: String,
    val respuesta1: String,
    val respuesta2: String,
    val respuesta3: String,
    val respuesta4: String,
    val respuestaCorrecta: String
)

object ProveedorPreguntas {
    fun obtenerPreguntas(context: Context): MutableList<Pregunta> {
        val jsonString: String = try {
            context.assets.open("preguntas.json")
                .bufferedReader()
                .use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return mutableListOf()
        }

        val listPreguntaType = object : TypeToken<List<Pregunta>>() {}.type
        val preguntas: List<Pregunta> = Gson().fromJson(jsonString, listPreguntaType)

        return preguntas.toMutableList()
    }
}