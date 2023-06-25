package com.example.vu.about

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.example.vu.ui.screens.about.AboutUs

import org.junit.Before
import org.junit.Rule
import org.junit.Test

class AboutTest {

    @get:Rule
    val composeTestRule = createComposeRule()
    private lateinit var navController: TestNavHostController

    @Before
    fun setupAppNavHost() {
        composeTestRule.setContent {
            // Initialize test NavHostController
            navController = TestNavHostController(LocalContext.current)
            // Adding navigator that navigates through composable
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            AboutUs()
        }
    }

    @Test
    fun checkAboutTitle(){
        composeTestRule.onNodeWithText("ABOUT US").assertIsDisplayed()
    }

}