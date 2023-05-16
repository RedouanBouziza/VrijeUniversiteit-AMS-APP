package com.example.vu.ui.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.vu.R

sealed class Screen(
    val route: String,
    val titleId: Int,
    val icon: ImageVector
) {
    object Home : Screen("home", R.string.home, Icons.Default.Home)
    object Chart : Screen("chart", R.string.chart, Icons.Default.Assessment)
    object BreathingSettings :
        Screen("breathingSettings", R.string.breathingSettings, Icons.Default.MonitorHeart)
    object BreathingExercise :
        Screen("BreathingExercise", R.string.breathingSettings, Icons.Default.MonitorHeart)
    object Setup : Screen("Setup", R.string.setup, Icons.Default.PhonelinkSetup)
    object Faq : Screen("Faq", R.string.faq, Icons.Default.Info)
    object Movement : Screen("Movement", R.string.movement, Icons.Default.Person)
    object System : Screen("System", R.string.system, Icons.Default.Settings)
    object Measurement : Screen("chart", R.string.measure, Icons.Default.AccountTree)
    object StartRecording :
        Screen("StartRecording", R.string.startRecording, Icons.Default.FiberManualRecord)
}