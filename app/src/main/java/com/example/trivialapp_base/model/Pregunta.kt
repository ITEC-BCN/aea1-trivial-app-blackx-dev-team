package com.example.trivialapp_base.model

import android.content.Context
import android.text.Html
import com.example.trivialapp_base.network.OTDBApi
import com.example.trivialapp_base.network.TokenManager
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
    // Utility function to decode HTML entities
    private fun decodeHtml(html: String): String {
        return Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY).toString()
    }

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

    private var categories: MutableMap<Int, String> = mutableMapOf()

    suspend fun getCategories(): MutableMap<Int, String> {
        return try {
            if (categories.isEmpty()) {
                val response = OTDBApi.retrofitService.getCategories()
                response.trivia_categories.forEach { category ->
                    categories[category.id] = decodeHtml(category.name)
                }
            }
            categories
        } catch (e: Exception) {
            e.printStackTrace()
            mutableMapOf()
        }
    }

    fun getCategoryIdByName(name: String): Int? {
        return categories.entries.find { it.value == name }?.key
    }

    suspend fun getQuestions(amount: Int = 10, category: Int? = null, difficulty: String? = null): List<Pregunta> {
        return try {
            val token: String? = TokenManager.getToken()
            val response = OTDBApi.retrofitService.getQuestions(amount, category, difficulty, token=token)
            response.results.map { question ->
                Pregunta(
                    pregunta = decodeHtml(question.question),
                    categoria = decodeHtml(question.category),
                    dificultad = question.difficulty,
                    respuesta1 = decodeHtml(question.incorrect_answers.getOrNull(0) ?: ""),
                    respuesta2 = decodeHtml(question.incorrect_answers.getOrNull(1) ?: ""),
                    respuesta3 = decodeHtml(question.incorrect_answers.getOrNull(2) ?: ""),
                    respuesta4 = decodeHtml(question.correct_answer),
                    respuestaCorrecta = decodeHtml(question.correct_answer)
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }
}

