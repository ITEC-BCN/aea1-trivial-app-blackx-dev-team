package com.example.trivialapp_base.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.trivialapp_base.Routes
import com.example.trivialapp_base.viewmodel.GameViewModel
import com.example.trivialapp_base.viewmodel.ResultadoPregunta

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResultScreen(navController: NavController, viewModel: GameViewModel) {
    val totalPreguntas = viewModel.historialResultados.size
    val aciertos = viewModel.puntuacion / 10
    val porcentaje = if (totalPreguntas > 0) (aciertos.toFloat() / totalPreguntas * 100).toInt() else 0

    Scaffold(
        topBar = {
            TopAppBar(

                title = { Text("Resultados") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate(Routes.Menu.route) }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Menu")
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Results Summary
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                    ) {
                        Text(text = "Puntos: ${viewModel.puntuacion}", fontSize = 24.sp, fontWeight = FontWeight.Bold)
                        Text(text = "Aciertos: $aciertos / $totalPreguntas", fontSize = 18.sp)
                        Text(text = "Porcentaje: $porcentaje%", fontSize = 18.sp)
                    }
                }
            }

            item {
                Button(modifier = Modifier.fillMaxWidth().padding(horizontal = 40.dp).height(60.dp), onClick = { navController.navigate(Routes.Menu.route) }) {
                    Text(text = "Jugar de nuevo", fontSize = 30.sp)
                }
            }

            item {
                Text(
                    text = "Detalles de la partida",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            // List of Questions
            items(viewModel.historialResultados) { resultado ->
                ResultItem(resultado)
            }
        }
    }
}

@Composable
fun ResultItem(resultado: ResultadoPregunta) {
    var expanded by remember { mutableStateOf(false) }
    val isCorrect = resultado.respuestaUsuario == resultado.pregunta.respuestaCorrecta

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { expanded = !expanded },
        colors = CardDefaults.cardColors(
            containerColor = if (isCorrect) Color(0xFFE8F5E9) else Color(0xFFFFEBEE)
        )
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = resultado.pregunta.pregunta,
                    modifier = Modifier.weight(1f),
                    fontWeight = FontWeight.Medium
                )
                Icon(
                    imageVector = if (expanded) Icons.Default.KeyboardArrowDown else Icons.Default.KeyboardArrowUp,
                    contentDescription = null
                )
            }

            AnimatedVisibility(visible = expanded) {
                Column(modifier = Modifier.padding(top = 8.dp)) {
                    val respuestas = listOf(
                        resultado.pregunta.respuesta1,
                        resultado.pregunta.respuesta2,
                        resultado.pregunta.respuesta3,
                        resultado.pregunta.respuesta4
                    )

                    respuestas.forEach { respuesta ->
                        val isSelected = respuesta == resultado.respuestaUsuario
                        val isCorrectAns = respuesta == resultado.pregunta.respuestaCorrecta
                        
                        val bgColor = when {
                            isCorrectAns -> Color(0xFFC8E6C9)
                            isSelected && !isCorrectAns -> Color(0xFFFFCDD2)
                            else -> Color.Transparent
                        }

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 2.dp)
                                .background(bgColor, shape = MaterialTheme.shapes.small)
                                .padding(8.dp)
                        ) {
                            Text(
                                text = respuesta,
                                color = if (isSelected || isCorrectAns) Color.Black else Color.Gray
                            )
                        }
                    }
                }
            }
        }
    }
}