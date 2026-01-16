package com.example.trivialapp_base

sealed class Routes(val route: String) {
    object Splash : Routes("splash")
    object Menu : Routes("menu")
    object Game : Routes("game")
    object Result : Routes("result")
}