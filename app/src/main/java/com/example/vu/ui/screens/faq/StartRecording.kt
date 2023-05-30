package com.example.vu.ui.screens.faq

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.vu.R
import com.example.vu.ui.screens.Screen
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import com.google.accompanist.pager.rememberPagerState
import com.google.android.material.math.MathUtils
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

@OptIn(ExperimentalPagerApi::class)
@Composable
fun StartRecording(navController: NavHostController) {
    var currentStep by remember { mutableStateOf(1) }
    var buttonText by remember { mutableStateOf("") }
    var explanationForEachStep by remember { mutableStateOf(currentStepExplanationRecording(currentStep)) }
    val images = imageForEachStepRecording(currentStep)
    val pagerState = rememberPagerState(initialPage = images.size)
    val scope = rememberCoroutineScope()

    val secondLastQuestion = 3
    val lastQuestion = 4


    Column(
        Modifier
            .padding(horizontal = 15.dp)
            .padding(top = 15.dp)
    ) {
        Text(
            text = stringResource(id = R.string.title_setup),
            style = MaterialTheme.typography.h6
        )

        Text(
            text = stringResource(id = R.string.step_recording, currentStep),
            Modifier.padding(top = 10.dp),
            style = MaterialTheme.typography.subtitle1
        )

        Text(
            text = stringResource(id = explanationForEachStep),
            Modifier.padding(top = 10.dp),
            style = MaterialTheme.typography.subtitle2
        )

        HorizontalPager(
            count = images.size,
            state = pagerState
        ) { page ->
            Card(
                Modifier
                    .graphicsLayer {
                        // Calculate the absolute offset for the current page from the
                        // scroll position. We use the absolute value which allows us to mirror
                        // any effects for both directions
                        val pageOffset = calculateCurrentOffsetForPage(page).absoluteValue

                        // We animate the scaleX + scaleY, between 85% and 100%
                        MathUtils.lerp(0.85f, 1f, 1f - pageOffset.coerceIn(0f, 1f)).also { scale ->
                            scaleX = scale
                            scaleY = scale
                        }

                        // We animate the alpha, between 50% and 100%
                        alpha = MathUtils.lerp(0.5f, 1f, 1f - pageOffset.coerceIn(0f, 1f))
                    }
            ) {
                Image(
                    painter = painterResource(images[page]),
                    contentDescription = "Steps images",
                    modifier = Modifier.fillMaxSize()
                )

                SideEffect {
                    when (pagerState.currentPage) {
                        images.indexOf(R.drawable.recording2_1) -> {
                            explanationForEachStep = R.string.step2_recording
                        }
                        images.indexOf(R.drawable.recording2_1) -> {
                            explanationForEachStep = R.string.step2_recording
                        }
                        images.indexOf(R.drawable.recording2_2) -> {
                            explanationForEachStep = R.string.step2_recording
                        }
//                        images.indexOf(R.drawable.recording2_3) -> {
//                            explanationForEachStep = R.string.step2a_recording
//                        }
//                        images.indexOf(R.drawable.step5_5) -> {
//                            explanationForEachStep = R.string.step5b_explanation
//                        }
//                        images.indexOf(R.drawable.step5_6) -> {
//                            explanationForEachStep = R.string.step5b_explanation
//                        }
//                        images.indexOf(R.drawable.step5_7) -> {
//                            explanationForEachStep = R.string.step5c_explanation
//                        }
//                        images.indexOf(R.drawable.step5_8) -> {
//                            explanationForEachStep = R.string.step5c_explanation
//                        }
//                        images.indexOf(R.drawable.step5_9) -> {
//                            explanationForEachStep = R.string.step5d_explanation
//                        }
//                        images.indexOf(R.drawable.step5_10) -> {
//                            explanationForEachStep = R.string.step5d_explanation
//                        }
//                        images.indexOf(R.drawable.step6_1) -> {
//                            explanationForEachStep = R.string.step6a_explanation
//                        }
//                        images.indexOf(R.drawable.step6_2) -> {
//                            explanationForEachStep = R.string.step6a_explanation
//                        }
//                        images.indexOf(R.drawable.step6_3) -> {
//                            explanationForEachStep = R.string.step6a_explanation
//                        }
//                        images.indexOf(R.drawable.step6_4) -> {
//                            explanationForEachStep = R.string.step6b_explanation
//                        }
                    }
                }

            }
        }
    }

    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.Bottom,
        modifier = Modifier
            .padding(bottom = 50.dp)
            .fillMaxSize()
    ) {
        images.forEachIndexed { index, _ ->
            Icon(
                painter = painterResource(if (pagerState.currentPage == index) R.drawable.baseline_circle_24 else R.drawable.outline_circle_24),
                contentDescription = "Page Indicator",
                modifier = Modifier.size(12.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
        }
    }


    // Bottom left button
    Row(
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.Bottom
    ) {
        if (currentStep > 1) {
            Button(
                onClick = {
                    currentStep--
                    scope.launch { pagerState.scrollToPage(0) }
                },
                modifier = Modifier.padding(start = 5.dp)
            ) {
                Text(text = "Previous")
            }
        }
    }

    Row(
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.Bottom
    ) {
        Button(
            shape = RoundedCornerShape(5.dp),
            onClick = {
                currentStep++
                scope.launch { pagerState.scrollToPage(0) }
            },
            modifier = Modifier.padding(end = 5.dp)
        ) {
            Text(text = buttonText)
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

}

fun imageForEachStepRecording(currentStep: Int): List<Int> {
    return when (currentStep) {
        1 -> {
            listOf(
                R.drawable.recording1
            )
        }
        2 -> {
            listOf(
                R.drawable.recording2_1,
                R.drawable.recording2_2,
                R.drawable.recording2_3
            )
        }
        else -> {
            listOf(

            )
        }
    }
}

/**
 * @author Kaan Uğur
 * @author Casey Kruijer
 * @author Redouan Bouziza
 */
fun currentStepExplanationRecording(currentStep: Int): Int {
    return when (currentStep) {
        1 -> R.string.step1_recording
        2 -> R.string.step2_recording
        3 -> R.string.step3_explanation
        4 -> R.string.step4_explanation
        5 -> R.string.step5a_explanation
        6 -> R.string.step6a_explanation
        else -> R.string.no_explanation
    }
}