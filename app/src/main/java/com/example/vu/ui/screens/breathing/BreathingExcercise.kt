package com.example.vu.ui.screens.breathing

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.vu.data.viewmodel.BreathingViewModel
import com.example.vu.R

@Composable
fun BreathingExercise(breathingViewModel: BreathingViewModel) {
    val infiniteTransition = rememberInfiniteTransition()
    val scale = remember { Animatable(1f) }
    val breathIn = breathingViewModel.breathIn.value!! * 1000
    val breathOut = breathingViewModel.breathOut.value!! * 1000
    val pause = breathingViewModel.pause.value!! * 1000

    LaunchedEffect(Unit) {
        scale.animateTo(1.5f, tween(breathIn, pause, LinearEasing))
        scale.animateTo(1f, tween(breathOut, pause, LinearEasing))
    }

    Column(
        Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(15.dp),
        horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(R.drawable.breathe),
                contentDescription = "",
                modifier = Modifier.scale(scale.value)
            )
    }
}