package com.example.vu.ui.screens.breathing

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.vu.R
import com.example.vu.data.viewmodel.BreathingViewModel
import com.example.vu.ui.screens.chart.SciChartSurfaceView
import kotlinx.coroutines.CoroutineScope

@Composable
fun BreathingExercise(breathingViewModel: BreathingViewModel, scope: CoroutineScope) {
    val scale = remember { Animatable(1f) }
    val breathIn = breathingViewModel.breathIn.value!! * 1000
    val breathOut = breathingViewModel.breathOut.value!! * 1000
    val pauseBreatheIn = breathingViewModel.pauseBreatheIn.value!! * 1000
    val pauseBreatheOut = breathingViewModel.pauseBreatheOut.value!! * 1000
    val breathesPerMinute = 60000 / (breathIn + pauseBreatheIn + breathOut + pauseBreatheOut)

    LaunchedEffect(Unit) {
        while (true) {
            scale.animateTo(1.5f, tween(breathIn, pauseBreatheIn, LinearEasing))
            scale.animateTo(1f, tween(breathOut, pauseBreatheOut, LinearEasing))
        }
    }

    Column {
        Column(
            Modifier
                .fillMaxWidth()
                .height(270.dp)
                .padding(15.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Image(
                painter = painterResource(R.drawable.breathe),
                contentDescription = "",
                modifier = Modifier.scale(scale.value)
            )
        }

        Column(
            Modifier
                .fillMaxHeight()
        ) {
            Text(stringResource(R.string.breathes_per_min, breathesPerMinute))
            SciChartSurfaceView()
        }
    }
}