package com.example.trivialapp_base.model

// Definición de la clase de datos Pregunta según requisitos
data class Pregunta(
    val pregunta: String,
    val categoria: String,
    val dificultad: String, // "Facil", "Medio", "Dificil"
    val respuesta1: String,
    val respuesta2: String,
    val respuesta3: String,
    val respuesta4: String,
    val respuestaCorrecta: String // Debe coincidir con una de las anteriores
)

// Objeto para simular la base de datos local (Hardcoded)
object ProveedorPreguntas {
    fun obtenerPreguntas(): MutableList<Pregunta> {
        val lista = mutableListOf<Pregunta>()

        /**
         * Generates generic debug questions.
         * Question: "Debug #ID"
         * Result 2 is always the correct one, marked with parenthesis.
         */
        fun addDebug(id: Int, dif: String) {
            val r1 = "Result 1"
            val r2 = "(Correcto) Result 2"
            val r3 = "Result 3"
            val r4 = "Result 4"
            
            val correct = r2
            // Shuffle so the correct answer (r2) is not always in the same button
            val opciones = listOf(r1, r2, r3, r4).shuffled()
            
            lista.add(
                Pregunta(
                    pregunta = "Debug #$id",
                    categoria = "Testing",
                    dificultad = dif,
                    respuesta1 = opciones[0],
                    respuesta2 = opciones[1],
                    respuesta3 = opciones[2],
                    respuesta4 = opciones[3],
                    respuestaCorrecta = correct
                )
            )
        }

        // Add 10 questions for each difficulty
        for (i in 1..10) addDebug(i, "Facil")
        for (i in 11..20) addDebug(i, "Medio")
        for (i in 21..30) addDebug(i, "Dificil")

        return lista
    }
}