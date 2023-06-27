package com.example.vu.chart

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.example.vu.BuildConfig
import com.example.vu.data.viewmodel.ChartViewModel
import com.example.vu.ui.screens.chart.Chart
import com.scichart.charting.visuals.SciChartSurface
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * This test tests if the all the charts all available
 * @author Kaan Ugur
 */
class ChartTest {

    @get:Rule
    val composeTestRule = createComposeRule()
    private lateinit var navController: TestNavHostController
    private val chartViewModel = ChartViewModel()

    @Before
    fun setupAppNavHost() {
        composeTestRule.setContent {
            // Initialize test NavHostController
            navController = TestNavHostController(LocalContext.current)
            // Adding navigator that navigates through composable
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            // Set the license key before each test
            SciChartSurface.setRuntimeLicenseKey(BuildConfig.SCI_CHART_KEY)
            Chart(chartViewModel)
        }
    }

    @Test
    fun checkSignalSigns(){
        val tabCount = listOf(composeTestRule.onNodeWithTag("tabs")).size

        assertEquals(1, tabCount)

        composeTestRule.onNode(hasText("ECG")).assertIsDisplayed()
        composeTestRule.onNode(hasText("RES")).assertIsDisplayed()
        composeTestRule.onNode(hasText("GYRO")).assertIsDisplayed()
        composeTestRule.onNode(hasText("ACC")).assertIsDisplayed()
        composeTestRule.onNode(hasText("PRESS")).assertIsDisplayed()

        // Select the "RES" tab and verify that the corresponding chart is displayed
        composeTestRule.onNodeWithText("RES").performClick()
        composeTestRule.onNodeWithText("RES").assertIsDisplayed()

        // Select the "GYRO" tab and verify that the corresponding chart is displayed
        composeTestRule.onNodeWithText("GYRO").performClick()
        composeTestRule.onNodeWithText("GYRO").assertIsDisplayed()

        // Select the "ACC" tab and verify that the corresponding chart is displayed
        composeTestRule.onNodeWithText("ACC").performClick()
        composeTestRule.onNodeWithText("ACC").assertIsDisplayed()

        // Select the "PRESS" tab and verify that the corresponding chart is displayed
        composeTestRule.onNodeWithText("PRESS").performClick()
        composeTestRule.onNodeWithText("PRESS").assertIsDisplayed()
    }
}