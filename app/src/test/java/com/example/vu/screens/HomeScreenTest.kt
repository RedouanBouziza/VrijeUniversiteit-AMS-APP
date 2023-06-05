package com.example.vu.screens

import androidx.compose.material.Surface
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.testing.TestNavHostController
import com.example.vu.data.viewmodel.BreathingViewModel
import com.example.vu.data.viewmodel.ChartViewModel
import com.example.vu.data.viewmodel.UDPViewModel
import com.example.vu.ui.screens.home.Home
import com.example.vu.ui.theme.VUTheme
import org.junit.Rule
import org.junit.Test

class HomeScreenTest {

    lateinit var navController: TestNavHostController


    @get:Rule
    val composeTestRule = createComposeRule()
    // use createAndroidComposeRule<YourActivity>() if you need access to
    // an activity

    @Test
    fun myTest() {
        // Start the app
        composeTestRule.setContent {
            VUTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    val scope = rememberCoroutineScope()
                    val breathingViewModel: BreathingViewModel = viewModel()
                    val chartViewModel: ChartViewModel = viewModel()
                    val udpViewModel: UDPViewModel = viewModel()

                    // Set up your mock data or dependencies here

                    // Render the home screen
                    Home(
                        modifier = Modifier,
                        navController = navController,
                        chartViewModel = chartViewModel
                    )
                }
            }
        }

        // Add your test assertions here
    }
}