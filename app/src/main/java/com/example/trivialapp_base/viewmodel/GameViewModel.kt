package com.example.trivialapp_base.viewmodel

import android.app.Application
import android.os.CountDownTimer
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.example.trivialapp_base.model.Pregunta
import com.example.trivialapp_base.model.ProveedorPreguntas

data class ResultadoPregunta(
    val pregunta: Pregunta,
    val respuestaUsuario: String
)

class GameViewModel(application: Application) : AndroidViewModel(application) {

    private var preguntasPartida: List<Pregunta> = emptyList()
    var modoJuego by mutableStateOf("Dificultad")
        private set
    var indicePreguntaActual by mutableIntStateOf(0)
        private set

    var preguntaActual by mutableStateOf<Pregunta?>(null)
        private set

    var respuestasMezcladas by mutableStateOf<List<String>>(emptyList())
        private set

    var puntuacion by mutableIntStateOf(0)
        private set

    var tiempoRestante by mutableFloatStateOf(1f)
        private set

    var juegoTerminado by mutableStateOf(false)
        private set

    var dificultadSeleccionada by mutableStateOf("Facil")
        private set
    var categoriaSelecionada by mutableStateOf("General")
        private set


    var historialResultados by mutableStateOf<List<ResultadoPregunta>>(emptyList())
        private set

    private var timer: CountDownTimer? = null
    private val TIEMPO_POR_PREGUNTA = 10000L // 10 segons
    
    fun setDificultad(dificultad: String) {
        dificultadSeleccionada = dificultad
    }

    fun setCategoria(categoria: String) {
        categoriaSelecionada = categoria
    }

    fun setModo(modo: String) {
        modoJuego = modo
    }

    fun iniciarJuego() {
        preguntasPartida = if (modoJuego == "Dificultad") {
            getPreguntasPartidaByDifficulty(dificultadSeleccionada)
        } else {
            getPreguntasPartidaByCategory(categoriaSelecionada)
        }

        puntuacion = 0
        indicePreguntaActual = 0
        juegoTerminado = false
        historialResultados = emptyList()

        cargarSiguientePregunta()
    }

    private fun obtenerTodasLasPreguntas(): List<Pregunta> {
        // Usamos getApplication() para obtener el contexto necesario para el JSON
        return ProveedorPreguntas.obtenerPreguntas(getApplication())
    }

    fun getPreguntasPartidaByDifficulty(difficulty: String): List<Pregunta> {
        return obtenerTodasLasPreguntas()
            .filter { it.dificultad == difficulty }
            .shuffled()
            .take(10)
    }

    fun getPreguntasPartidaByCategory(category: String): List<Pregunta> {
        return obtenerTodasLasPreguntas()
            .filter { it.categoria == category }
            .shuffled()
            .take(10)
    }

    fun getCategoriesFromQuestions(): List<String> {
        return obtenerTodasLasPreguntas()
            .map { it.categoria }
            .distinct()
            .sorted()
    }

    private fun cargarSiguientePregunta() {
        if (indicePreguntaActual < preguntasPartida.size) {
            val pregunta = preguntasPartida[indicePreguntaActual]
            preguntaActual = pregunta

            respuestasMezcladas = listOf(
                pregunta.respuesta1,
                pregunta.respuesta2,
                pregunta.respuesta3,
                pregunta.respuesta4
            ).shuffled()

            iniciarTimer()
        } else {
            preguntaActual = null
            juegoTerminado = true
            timer?.cancel()
        }
    }

    fun responderPregunta(respuestaUsuario: String) {
        timer?.cancel()
        preguntaActual?.let {
            historialResultados = historialResultados + ResultadoPregunta(it, respuestaUsuario)
            if (respuestaUsuario == it.respuestaCorrecta) {
                puntuacion += 10
            }
        }
        avanzarRonda()
    }

    private fun avanzarRonda() {
        indicePreguntaActual++
        cargarSiguientePregunta()
    }

    private fun iniciarTimer() {
        timer?.cancel()
        timer = object : CountDownTimer(TIEMPO_POR_PREGUNTA, 100) {
            override fun onTick(millisUntilFinished: Long) {
                tiempoRestante = millisUntilFinished.toFloat() / TIEMPO_POR_PREGUNTA
            }

            override fun onFinish() {
                tiempoRestante = 0f
                responderPregunta("") // Se agota el tiempo, respuesta vacía
            }
        }.start()
    }


    override fun onCleared() {
        super.onCleared()
        timer?.cancel()
    }
}