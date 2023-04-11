package com.example.vu.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.Slider
import androidx.compose.material.SliderColors
import androidx.compose.material.SliderDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.vu.R
import kotlin.math.roundToInt

@Composable
fun Breathing(navController: NavController) {
    var sliderInValue by remember { mutableStateOf(1f) }
    var sliderOutValue by remember { mutableStateOf(1f) }
    Column(
        Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .padding(15.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(stringResource(R.string.breathe_in))
        Text("${sliderInValue.roundToInt()} " + stringResource(R.string.seconds))
        Slider(
            value = sliderInValue,
            onValueChange = {
                sliderInValue = it
            },
            valueRange = 1f..10f,
            steps = 10,
            colors = SliderDefaults.colors(Color(R.color.ams)),
        )

        Text(stringResource(R.string.breathe_out))
        Text("${sliderOutValue.roundToInt()} " + stringResource(R.string.seconds))
        Slider(
            value = sliderOutValue,
            onValueChange = {
                sliderOutValue = it
            },
            valueRange = 1f..10f,
            steps = 10,
            colors = SliderDefaults.colors(Color(R.color.ams)),
        )
    }
}