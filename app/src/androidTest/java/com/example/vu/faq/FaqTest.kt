package com.example.vu.faq

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.example.vu.ui.screens.faq.Faq
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 *
 * @author Kaan Ugur
 */
class FaqTest {

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
            Faq()
        }
    }

    @Test
    fun testFaq() {

        composeTestRule.onNode(hasText("Why is the LED on the VU-AMS 5fs blinking rapidly?"))
            .assertIsDisplayed()
        composeTestRule.onNode(hasText("Why does the VU-AMS 5fs make a beeping sound?"))
            .assertIsDisplayed()
        composeTestRule.onNode(hasText("How do I interface with stimulus presentation software?"))
            .assertIsDisplayed()
        composeTestRule.onNode(hasText("Can I export my ECG data for use in the Kubios HRV analysis software package?"))
            .assertIsDisplayed()

    }

}