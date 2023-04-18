package com.example.vu.ui.screens.breathing

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.vu.R
import java.util.Date

@Composable
fun BreathingExercise(breathingViewModel: BreathingViewModel) {
    val breathIn = breathingViewModel.breathIn.value!! * 1000
    val breathOut = breathingViewModel.breathOut.value!! * 1000
    val pause = breathingViewModel.pause.value!! * 1000

    val infiniteransition = rememberInfiniteTransition()
    val imageSize by infiniteransition.animateFloat(
        initialValue = 100.0f,
        targetValue =250.0f ,
        animationSpec = infiniteRepeatable(
            tween(breathIn, pause, LinearEasing),
            RepeatMode.Reverse
        )
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
                .size(imageSize.dp)

        )
    }
}