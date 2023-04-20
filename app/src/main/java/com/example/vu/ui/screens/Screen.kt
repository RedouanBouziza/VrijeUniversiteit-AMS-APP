package com.example.vu.ui.screens

sealed class Screen(
    val route: String
) {
    object Home: Screen("home")
    object Chart: Screen("chart")
    object BreathingSettings: Screen("breathingSettings")
    object BreathingExercise: Screen("BreathingExercise")
    object Setup: Screen("Setup")
    object Faq: Screen("Faq")
}

