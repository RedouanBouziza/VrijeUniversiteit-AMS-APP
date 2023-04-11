package com.example.vu.ui.screens

sealed class Screen(
    val route: String
) {
    object Home: Screen("home")
    object Chart: Screen("chart")
    object Breathing: Screen("breathing")
}

