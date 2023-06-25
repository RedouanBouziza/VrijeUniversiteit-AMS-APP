package com.example.vu.ui.screens.movement

import android.content.Context
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kotlin.math.min

@Composable
fun Movement(navController: NavHostController) {

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(668.dp)
                .padding(16.dp)
                .clip(RoundedCornerShape(10.dp)),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.padding(10.dp),
                text = "Not available for now",)
        }
    }

    //TODO: Implement movement with right values and add 3d image of the body
    /*Box(modifier = Modifier.fillMaxSize()) {
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
    }*/
}


@Composable
fun rememberOrientation(context: Context): Orientation {
    return remember(context) {
        Orientation(context)
    }
}