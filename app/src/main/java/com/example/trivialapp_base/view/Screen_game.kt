package com.example.trivialapp_base.view

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.trivialapp_base.Routes
import com.example.trivialapp_base.viewmodel.GameViewModel

@Composable
fun GameScreen(navController: NavController, viewModel: GameViewModel) {
    val preguntaActual = viewModel.preguntaActual
    val indice = viewModel.indicePreguntaActual
    val tiempo = viewModel.tiempoRestante
    val respuestas = viewModel.respuestasMezcladas

    var showExitDialog by remember { mutableStateOf(false) }

    val animatedProgress by animateFloatAsState(
        targetValue = tiempo,
        animationSpec = tween(durationMillis = 100),
        label = "progressAnimation"
    )

    // Intercept back button
    BackHandler {
        showExitDialog = true
    }

    // Observe if the game is over to navigate to result screen
    LaunchedEffect(viewModel.juegoTerminado) {
        if (viewModel.juegoTerminado) {
            navController.navigate(Routes.Result.route) {
                popUpTo(Routes.Game.route) { inclusive = true }
            }
        }
    }

    if (showExitDialog) {
        AlertDialog(
            onDismissRequest = { showExitDialog = false },
            title = { Text("¿Salir del juego?") },
            text = { Text("Se perderá todo el progreso actual.") },
            confirmButton = {
                TextButton(onClick = { navController.navigate(Routes.Menu.route) }) {
                    Text("Salir")
                }
            },
            dismissButton = {
                TextButton(onClick = { showExitDialog = false }) {
                    Text("Cancelar")
                }
            }
        )
    }

    if (preguntaActual != null) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Top Bar
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { showExitDialog = true }) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "Salir")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Progress Bar (Timer)
            LinearProgressIndicator(
                progress = { animatedProgress },
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(8.dp),
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Question Count
            Text(
                text = "${indice + 1}/10",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Question Box
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = preguntaActual.pregunta,
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(48.dp))

            // Answer Buttons Grid (2x2)
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    AnswerButton(text = respuestas.getOrNull(0) ?: "", onClick = { viewModel.responderPregunta(respuestas[0]) }, modifier = Modifier.weight(1f))
                    AnswerButton(text = respuestas.getOrNull(1) ?: "", onClick = { viewModel.responderPregunta(respuestas[1]) }, modifier = Modifier.weight(1f))
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    AnswerButton(text = respuestas.getOrNull(2) ?: "", onClick = { viewModel.responderPregunta(respuestas[2]) }, modifier = Modifier.weight(1f))
                    AnswerButton(text = respuestas.getOrNull(3) ?: "", onClick = { viewModel.responderPregunta(respuestas[3]) }, modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
fun AnswerButton(text: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Button(
        onClick = onClick,
        modifier = modifier.height(60.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Text(text = text, textAlign = TextAlign.Center)
    }
}