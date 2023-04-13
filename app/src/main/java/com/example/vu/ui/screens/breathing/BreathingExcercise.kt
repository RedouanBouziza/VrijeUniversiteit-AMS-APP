package com.example.vu.ui.screens.breathing

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.vu.R

enum class BreathingExcercisePosition {
    Start, Finish
}

@Composable
fun BreathingExercise() {
    var imageState by remember { mutableStateOf(BreathingExcercisePosition.Start) }
    val offsetAnimation: Dp by animateDpAsState(
        if (imageState == BreathingExcercisePosition.Start) 200.dp else 250.dp
    )

    Column(
        Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .padding(15.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(R.drawable.breathe),
            contentDescription = "",
            modifier = Modifier
                .height(offsetAnimation)
                .width(offsetAnimation)
        )

        Button(onClick = {
            imageState = when (imageState) {
                BreathingExcercisePosition.Start -> BreathingExcercisePosition.Finish
                BreathingExcercisePosition.Finish -> BreathingExcercisePosition.Start
            }
        }) {
            Text("click")
        }
    }
}