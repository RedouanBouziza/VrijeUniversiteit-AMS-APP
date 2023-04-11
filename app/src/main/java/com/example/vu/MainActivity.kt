package com.example.vu

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.vu.ui.screens.Breathing
import com.example.vu.ui.screens.Chart
import com.example.vu.ui.screens.Home
import com.example.vu.ui.screens.Screen
import com.example.vu.ui.theme.VUTheme

/**
 * @author Casey Kruijer
 * @author Kaan Uğur
 * @author Redouan Bouziza
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent()
    }

    private fun setContent() {
        setContent {
            VUTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    ScreenContent(modifier = Modifier)
                }
            }
        }
    }
}

@Composable
private fun ScreenContent(modifier: Modifier) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Breathing.route,
        modifier = modifier
    ) {
        composable(route = Screen.Home.route) {
            Home(navController)
        }
        composable(route = Screen.Chart.route) {
            Chart(navController)
        }
        composable(route = Screen.Breathing.route) {
            Breathing(navController)
        }
    }
}