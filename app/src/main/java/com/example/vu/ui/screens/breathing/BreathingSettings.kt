package com.example.vu.ui.screens.breathing

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.vu.R
import com.example.vu.data.viewmodel.BreathingViewModel
import com.example.vu.ui.screens.Screen
import java.util.*
import kotlin.math.roundToInt

@Composable
fun BreathingSettings(
    navController: NavController,
    breathingViewModel: BreathingViewModel,
) {
    var breatheIn by remember { mutableStateOf(2f) }
    var breatheOut by remember { mutableStateOf(2f) }
    var pauseBreatheIn by remember { mutableStateOf(0f) }
    var pauseBreatheOut by remember { mutableStateOf(1f) }

    Column(
        Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(stringResource(R.string.breathes_per_min1))

        Row {
            Button(
                onClick = {
                    breatheIn = 2f
                    breatheOut = 2f
                    pauseBreatheIn = 1f
                    pauseBreatheOut = 1f
                },
                Modifier.padding(end = 5.dp)
            ) {
                Text("10")
            }

            Button(
                onClick = {
                    breatheIn = 1f
                    breatheOut = 1f
                    pauseBreatheIn = 1f
                    pauseBreatheOut = 1f
                },
                Modifier.padding(end = 5.dp)
            ) {
                Text("15")
            }

            Button(
                onClick = {
                    breatheIn = 1f
                    breatheOut = 1f
                    pauseBreatheIn = 0f
                    pauseBreatheOut = 1f
                },
                Modifier.padding(end = 5.dp)
            ) {
                Text("20")
            }
        }
    }

    Column(
        Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .padding(15.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(stringResource(R.string.breathe_in))
        Text("${breatheIn.roundToInt()} " + stringResource(R.string.seconds))
        Slider(
            value = breatheIn,
            onValueChange = {
                breatheIn = it
            },
            valueRange = 1f..20f,
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

        Text(stringResource(R.string.pause_breathe_in))
        Text("${pauseBreatheIn.roundToInt()} " + stringResource(R.string.seconds))
        Slider(
            value = pauseBreatheIn,
            onValueChange = {
                pauseBreatheIn = it
            },
            valueRange = 0f..20f,
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
        Text("${breatheOut.roundToInt()} " + stringResource(R.string.seconds))
        Slider(
            value = breatheOut,
            onValueChange = {
                breatheOut = it
            },
            valueRange = 1f..20f,
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

        Text(stringResource(R.string.pause_breathe_out))
        Text("${pauseBreatheOut.roundToInt()} " + stringResource(R.string.seconds))
        Slider(
            value = pauseBreatheOut,
            onValueChange = {
                pauseBreatheOut = it
            },
            valueRange = 0f..20f,
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
            onClick = {
                breathingViewModel.breathIn.value = breatheIn.roundToInt()
                breathingViewModel.breathOut.value = breatheOut.toInt()
                breathingViewModel.pauseBreatheIn.value = pauseBreatheIn.toInt()
                breathingViewModel.pauseBreatheOut.value = pauseBreatheOut.toInt()
                navController.navigate(Screen.BreathingExercise.route)
            },
            colors = ButtonDefaults.buttonColors(colorResource(R.color.ams))
        ) {
            Text(stringResource(R.string.start).uppercase(Locale.ROOT))
        }
    }
}