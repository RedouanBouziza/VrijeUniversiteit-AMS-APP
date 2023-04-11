package com.example.vu.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import com.example.vu.R
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun Home(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopBar()
        }
    ) {
    Text(text = "Home Page")
    }
}

@Composable
fun TopBar() {
    TopAppBar(
        backgroundColor = colorResource(id = R.color.ams),
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.ams),
                    color = Color.White)
            }
        }
    )
}




