package com.example.vu

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.vu.data.viewmodel.ChartViewModel
import com.example.vu.ui.screens.chart.Chart
import com.example.vu.ui.theme.VUTheme
import org.junit.Rule
import org.junit.Test

//class ChartTest {
//
//    @get:Rule
//    val composeTestRule = createComposeRule()
//
//    @Test
//    fun testChart() {
//        // Create a mock instance of ChartViewModel
//        val mockChartViewModel = object : ChartViewModel() {
//            // Implement necessary functions or properties for testing
//            override val sectionAMeasurements: MutableLiveData<TreeMap<String, SectionMeasurement>> = MutableLiveData()
//        }
//
//        // Set up initial state or data in the ChartViewModel if needed
//        val initialData = TreeMap<String, SectionMeasurement>()
//        mockChartViewModel.sectionAMeasurements.value = initialData
//
//        // Call the Chart composable function with the mock ChartViewModel
//        composeTestRule.setContent {
//            Chart(mockChartViewModel)
//        }
//
//        // Perform UI tests and assertions
//        // Example: Verify that the correct number of Tab components are rendered
//        composeTestRule.onAllNodesWithTag("Tab").assertCountEquals(6)
//    }
//}