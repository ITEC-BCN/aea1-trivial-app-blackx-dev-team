package com.example.trivialapp_base

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.trivialapp_base.network.TokenManager
import com.example.trivialapp_base.ui.theme.TrivialAPP_BaseTheme
import com.example.trivialapp_base.view.GameScreen
import com.example.trivialapp_base.view.MenuScreen
import com.example.trivialapp_base.view.ResultScreen
import com.example.trivialapp_base.view.SplashScreen
import com.example.trivialapp_base.viewmodel.GameViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        TokenManager.init(this)
        enableEdgeToEdge()
        setContent {
            TrivialAPP_BaseTheme {
                // Instanciamos el ViewModel una vez
                val gameViewModel: GameViewModel by viewModels()

                // Definición de rutas y navegación
                Navigation(gameViewModel)
            }
        }

    }
}

@Composable
fun Navigation(viewModel: GameViewModel) {
    // Controlador de navegación
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.Splash.route
    ) {
        composable(Routes.Splash.route) {
            SplashScreen(navController)
        }
        composable(Routes.Menu.route) {
            MenuScreen(navController, viewModel)
        }
        composable(Routes.Game.route) {
            GameScreen(navController, viewModel)
        }
        composable(Routes.Result.route) {
            ResultScreen(navController, viewModel)
        }
    }
}