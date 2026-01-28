package com.example.trivialapp_base.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.trivialapp_base.R
import com.example.trivialapp_base.Routes
import com.example.trivialapp_base.viewmodel.GameViewModel

@Composable
fun MenuScreen(navController: NavController, viewModel: GameViewModel) {
    val dificultades = listOf("Easy", "Medium", "Hard")
    var dificultyExpanded by remember { mutableStateOf(false) }
    var categoryExpanded by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.loadCategoriesFromApi()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.height(70.dp))

        Text(
            text = "BeerVial APP",
            fontSize = 48.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Image(
            painter = painterResource(id = R.drawable.beervial),
            contentDescription = "BeerVial App",
            modifier = Modifier.size(180.dp)
        )
        Spacer(modifier = Modifier.height(70.dp))

        Spacer(modifier = Modifier.height(20.dp))


        Text(text = "Select difficulty:", fontSize = 20.sp)

        Box {
            OutlinedButton(onClick = { dificultyExpanded = true },
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .height(56.dp))
            {
                Text(text = viewModel.dificultadSeleccionada, fontSize = 20.sp)
            }
            DropdownMenu(
                expanded = dificultyExpanded,
                onDismissRequest = { dificultyExpanded = false }
            ) {
                dificultades.forEach { dificultad ->
                    DropdownMenuItem(
                        text = { Text(dificultad) },
                        onClick = {
                            viewModel.setDificultad(dificultad.lowercase())
                            dificultyExpanded = false
                        }
                    )
                }
            }
        }

        Text(text = "Select la category:", fontSize = 20.sp)

        Box {
            OutlinedButton(onClick = { categoryExpanded = true },
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .height(56.dp))
            {
                Text(text = viewModel.categoriaSelecionada, fontSize = 20.sp)
            }
            DropdownMenu(
                expanded = categoryExpanded,
                onDismissRequest = { categoryExpanded = false }
            ) {
                viewModel.categoriesFromApi.forEach { category ->
                    DropdownMenuItem(
                        text = { Text(category) },
                        onClick = {
                            viewModel.setCategoria(category)
                            categoryExpanded = false
                        }
                    )
                }
            }
        }


        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                viewModel.iniciarJuego()
                navController.navigate(Routes.Game.route)
            },
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .height(56.dp)
        ) {
            Text(text = "PLAY", fontSize = 20.sp)
        }


        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = "© All rights reserved, BlackX dev team",
                fontSize = 13.sp,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 8.dp),
                color = Color.Gray
            )
        }
    }
}