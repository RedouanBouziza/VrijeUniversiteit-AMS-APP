package com.example.vu.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import com.scichart.charting.visuals.SciChartSurface

@Composable
fun Chart(navController: NavHostController) {
    SciChartSurfaceView()
}


@Composable
fun SciChartSurfaceView() {
    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { context ->
            SciChartSurface(context).apply {
                // add any custom configuration here
            }
        },
        update = {
            // add any update logic here
        }
    )
}