package com.example.vu.ui.screens.breathing

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.vu.R
import com.example.vu.data.viewmodel.BreathingViewModel
import kotlinx.coroutines.CoroutineScope

@Composable
fun BreathingExercise(scope: CoroutineScope, breathingViewModel: BreathingViewModel) {
    val infiniteTransition = rememberInfiniteTransition()
    val breathIn = breathingViewModel.breathIn.value!! * 1000
    val breathOut = breathingViewModel.breathOut.value!! * 1000
    val pause = breathingViewModel.pause.value!! * 1000

    val imageSize by infiniteTransition.animateFloat(
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