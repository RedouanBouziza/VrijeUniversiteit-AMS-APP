package com.example.vu.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.vu.R
import java.util.*
import kotlin.math.roundToInt

@Composable
fun BreathingSettings(navController: NavController) {
    var sliderInValue by remember { mutableStateOf(1f) }
    var sliderOutValue by remember { mutableStateOf(1f) }
    var pause by remember { mutableStateOf(1f) }
    var maxDuration by remember { mutableStateOf(1f) }

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
            colors = SliderDefaults.colors(
                thumbColor = colorResource(R.color.white),
                activeTickColor = colorResource(R.color.ams),
                inactiveTickColor = colorResource(R.color.white),
                activeTrackColor = colorResource(R.color.white),
                inactiveTrackColor = colorResource(R.color.ams),
            ),
            modifier = Modifier.padding(bottom = 30.dp),
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
            colors = SliderDefaults.colors(
                thumbColor = colorResource(R.color.white),
                activeTickColor = colorResource(R.color.ams),
                inactiveTickColor = colorResource(R.color.white),
                activeTrackColor = colorResource(R.color.white),
                inactiveTrackColor = colorResource(R.color.ams),
            ),
            modifier = Modifier.padding(bottom = 30.dp),
        )

        Text(stringResource(R.string.pause))
        Text("${pause.roundToInt()} " + stringResource(R.string.seconds))
        Slider(
            value = pause,
            onValueChange = {
                pause = it
            },
            valueRange = 1f..10f,
            steps = 10,
            colors = SliderDefaults.colors(
                thumbColor = colorResource(R.color.white),
                activeTickColor = colorResource(R.color.ams),
                inactiveTickColor = colorResource(R.color.white),
                activeTrackColor = colorResource(R.color.white),
                inactiveTrackColor = colorResource(R.color.ams),
            ),
            modifier = Modifier.padding(bottom = 30.dp),
        )

        Text(stringResource(R.string.max_duration))
        Text("${maxDuration.roundToInt()} " + stringResource(R.string.minutes))
        Slider(
            value = maxDuration,
            onValueChange = {
                maxDuration = it
            },
            valueRange = 1f..10f,
            steps = 10,
            colors = SliderDefaults.colors(
                thumbColor = colorResource(R.color.white),
                activeTickColor = colorResource(R.color.ams),
                inactiveTickColor = colorResource(R.color.white),
                activeTrackColor = colorResource(R.color.white),
                inactiveTrackColor = colorResource(R.color.ams),
            ),
            modifier = Modifier.padding(bottom = 30.dp),
        )

        Button(
            onClick = { /*TODO*/ },
            colors = ButtonDefaults.buttonColors(colorResource(R.color.ams))
        ) {
            Text(stringResource(R.string.start).uppercase(Locale.ROOT))
        }
    }
}