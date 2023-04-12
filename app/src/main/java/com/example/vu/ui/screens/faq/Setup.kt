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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.vu.R
import com.example.vu.ui.screens.TopBar

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun Setup(navController: NavHostController) {

    Scaffold(topBar = { TopBar() }
    ) {
        SetupInstructions()
    }


}

@Composable
private fun SetupInstructions() {
    var currentStep by remember { mutableStateOf(1) }

    Column(
        Modifier
            .padding(horizontal = 15.dp)
            .padding(top = 15.dp)
    ) {
        Text(
            text = "How to connect the electrodes",
            style = MaterialTheme.typography.h5
        )

        Text(
            text = "Step $currentStep: ",
            Modifier.padding(top = 50.dp),
            style = MaterialTheme.typography.subtitle1
        )

        Image(
            painter = painterResource(id = R.drawable.ams),
            modifier = Modifier.padding(top = 15.dp),
            contentDescription = "Instruction image"
        )

    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter,
    ) {
        Button(
            shape = RoundedCornerShape(5.dp),
            onClick = { /* Handle button click */ }
        ) {
            Text(text = "Next Step")
        }
    }
}