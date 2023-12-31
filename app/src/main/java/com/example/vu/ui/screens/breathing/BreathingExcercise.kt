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
import com.example.vu.data.viewmodel.ChartViewModel
import com.example.vu.ui.screens.chart.ChartType
import kotlin.math.roundToInt

@Composable
fun BreathingExercise(
    breathingViewModel: BreathingViewModel,
    chartViewModel: ChartViewModel
) {
    val scale = remember { Animatable(1f) }
    val breatheIn = remember { (breathingViewModel.breathIn.value!! * 1000).roundToInt() }
    val breatheOut = remember { (breathingViewModel.breathOut.value!! * 1000).roundToInt() }
    val pauseBreatheIn = remember { (breathingViewModel.pauseBreatheIn.value!! * 1000).roundToInt() }
    val pauseBreatheOut = remember { (breathingViewModel.pauseBreatheOut.value!! * 1000).roundToInt() }
    val breathesPerMinute = remember { 60000 / (breatheIn + pauseBreatheIn + breatheOut + pauseBreatheOut) }

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

            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(668.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    ChartType(chartViewModel, "RES")
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        while (true) {
            scale.animateTo(1.5f, tween(breatheIn, pauseBreatheIn, LinearEasing))
            scale.animateTo(1f, tween(breatheOut, pauseBreatheOut, LinearEasing))
        }
    }
}