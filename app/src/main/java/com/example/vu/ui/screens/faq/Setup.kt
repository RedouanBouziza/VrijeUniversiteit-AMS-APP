package com.example.vu.ui.screens.faq

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.vu.R
import com.example.vu.ui.screens.Screen
import com.example.vu.ui.screens.TopBar

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun Setup(navController: NavHostController) {

    Scaffold(topBar = { TopBar() }
    ) {
        SetupInstructions(navController)
    }
}

@Composable
private fun SetupInstructions(navController: NavHostController) {
    var currentStep by remember { mutableStateOf(1) }
    var buttonText by remember { mutableStateOf("") }
    val secondLastQuestion = 4
    val lastQuestion = 5



    Column(
        Modifier
            .padding(horizontal = 15.dp)
            .padding(top = 15.dp)
    ) {
        Text(
            text = stringResource(id = R.string.title_setup),
            style = MaterialTheme.typography.h5
        )

        Text(
            text = stringResource(id = R.string.step, currentStep),
            Modifier.padding(top = 50.dp),
            style = MaterialTheme.typography.subtitle1
        )

        Text(
            text = stringResource(id = currentStepExplanation(currentStep)),
            Modifier.padding(top = 50.dp),
            style = MaterialTheme.typography.subtitle2
        )
    }

    Image(
        painter = painterResource(id = currentStepImage(currentStep)),
        modifier = Modifier.fillMaxSize(),
        contentDescription = "Instruction image"
    )

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter,
    ) {
        Button(
            shape = RoundedCornerShape(5.dp),
            onClick = { currentStep++ }
        ) {
            Text(text = buttonText)
        }
    }

    when (currentStep) {
        secondLastQuestion -> {
            buttonText = stringResource(id = R.string.finish)
        }
        lastQuestion -> {
            LaunchedEffect(Unit) {
                navController.navigate(Screen.Home.route)
            }
        }
        else -> {
            buttonText = stringResource(id = R.string.next_step)
        }
    }
}

fun currentStepImage(currentStep: Int): Int {
    return when (currentStep) {
        1 -> R.drawable.eggman
        2 -> R.drawable.ams
        3 -> R.drawable.eggman
        else -> R.drawable.eggman
    }
}

fun currentStepExplanation(currentStep: Int): Int {
    return when (currentStep) {
        1 -> R.string.step1_explanation
        2 -> R.string.step2_explanation
        3 -> R.string.step3_explanation
        else -> R.string.step4_explanation
    }
}