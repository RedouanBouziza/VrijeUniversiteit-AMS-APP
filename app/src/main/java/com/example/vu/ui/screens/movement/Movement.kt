package com.example.vu.ui.screens.movement

import android.content.Context
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import kotlin.math.min

@Composable
fun Movement(navController: NavHostController) {
    Box(modifier = Modifier.fillMaxSize()) {
        val pitch = remember { mutableStateOf(0f) }
        val roll = remember { mutableStateOf(0f) }
        val yaw = remember { mutableStateOf(0f) }
        val context = LocalContext.current
        val orientation = rememberOrientation(context)

        orientation.listener = { p, r, y ->
            pitch.value = p
            roll.value = r
            yaw.value = y
        }
        orientation.startListening()

        Canvas(modifier = Modifier.fillMaxSize()) {
            val canvasWidth = size.width
            val canvasHeight = size.height
            val centerX = canvasWidth / 2
            val centerY = canvasHeight / 2

            val radius = min(canvasWidth, canvasHeight) / 8

            val ballX = centerX + (yaw.value / 90f) * (canvasWidth / 2 - radius)
            val ballY = centerY - (pitch.value / 90f) * (canvasHeight / 2 - radius)

            drawCircle(
                color = Color.Red,
                radius = radius,
                center = Offset(ballX, ballY)
            )
        }

        Canvas(modifier = Modifier.fillMaxSize()) {
            val canvasWidth = size.width
            val canvasHeight = size.height
            val centerX = canvasWidth / 2
            val centerY = canvasHeight / 2

            val radius = min(canvasWidth, canvasHeight) / 16

            val ballX = centerX + (roll.value / 90f) * (canvasWidth / 2 - radius)

            drawCircle(
                color = Color.Blue,
                radius = radius,
                center = Offset(ballX, centerY)
            )
        }
    }
}


@Composable
fun rememberOrientation(context: Context): Orientation {
    return remember(context) {
        Orientation(context)
    }
}