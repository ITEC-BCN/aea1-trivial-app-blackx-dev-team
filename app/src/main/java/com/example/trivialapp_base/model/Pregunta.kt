package com.example.trivialapp_base.model

import android.content.Context
import android.text.Html
import com.example.trivialapp_base.network.OTDBApi
import com.example.trivialapp_base.network.TokenManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException
import kotlinx.coroutines.delay
import retrofit2.HttpException

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
                delay(1000)
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

    suspend fun getCategoryIdByName(name: String): Int? {
        if (categories.isEmpty()) {
            getCategories()
        }
        return categories.entries.find { it.value == name }?.key
    }

    suspend fun getQuestions(amount: Int = 10, category: Int? = null, difficulty: String? = null, retryCount: Int = 0): List<Pregunta> {
        return try {
            val token: String? = TokenManager.getToken()
            // Increase delay between requests to avoid rate limiting
            delay((1000 + (retryCount * 500)).toLong())
            val response = OTDBApi.retrofitService.getQuestions(amount, category, difficulty, token=token)

            if (response.response_code == 429) {
                if (retryCount < 3) {
                    delay((5000 + (retryCount * 2000)).toLong())
                    return getQuestions(amount, category, difficulty, retryCount + 1)
                }
                emptyList()
            } else if (response.response_code == 0) {
                // Success
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
            } else {
                // Other error codes
                emptyList()
            }
        } catch (e: HttpException) {
            // Handle HTTP 429 Too Many Requests exception
            if (e.code() == 429 && retryCount < 3) {
                delay((5000 + (retryCount * 2000)).toLong())
                return getQuestions(amount, category, difficulty, retryCount + 1)
            }
            e.printStackTrace()
            emptyList()
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }
}

