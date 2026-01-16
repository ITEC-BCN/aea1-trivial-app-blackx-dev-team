package com.example.trivialapp_base.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.trivialapp_base.R
import com.example.trivialapp_base.Routes
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {

    // Navega a Menu después de 2 segundos
    LaunchedEffect(true) {
        delay(2000)
        navController.navigate(Routes.Menu.route) {
            popUpTo(Routes.Splash.route) { inclusive = true }
        }
    }

    // Contenedor centrado
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        // Column para imagen + texto
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(id = R.drawable.beervial), // Cambia "logo" por tu imagen
                contentDescription = "BeerVial App",
                modifier = Modifier.size(180.dp)
            )
            Spacer(modifier = Modifier.height(16.dp)) // Espacio entre logo y texto
            Text(
                text = "BeerVial APP",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}