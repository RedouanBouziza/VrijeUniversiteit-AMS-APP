package com.example.vu.ui.screens.faq

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
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
fun SetupConnection(navController: NavHostController) {

    var currentStep by remember { mutableStateOf(1) }
    var buttonText by remember { mutableStateOf("") }
    var explanationForEachStep by remember { mutableStateOf(currentStepExplanationConnection(currentStep)) }
    val images = imageForEachStepConnection(currentStep)
    val pagerState = rememberPagerState(initialPage = images.size)
    val showDialog = remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    val secondLastQuestion = 7
    val lastQuestion = 8


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
            text = stringResource(id = R.string.step_connection, currentStep),
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
                    modifier = Modifier.size(400.dp)
                )

                SideEffect {
                    when (pagerState.currentPage) {
                        images.indexOf(R.drawable.recording1) -> {
                            explanationForEachStep = R.string.step1_connection
                        }
                        images.indexOf(R.drawable.recording2_1) -> {
                            explanationForEachStep = R.string.step2_connection
                        }
                        images.indexOf(R.drawable.recording2_2) -> {
                            explanationForEachStep = R.string.step2_connection
                        }
                        images.indexOf(R.drawable.recording2_3) -> {
                            explanationForEachStep = R.string.step2_connection
                        }
                        images.indexOf(R.drawable.recording2_3) -> {
                            explanationForEachStep = R.string.step3_connection
                        }
                        images.indexOf(R.drawable.recording2_3) -> {
                            explanationForEachStep = R.string.step4_connection
                        }
                        images.indexOf(R.drawable.recording2_3) -> {
                            explanationForEachStep = R.string.step5_connection
                        }
                        images.indexOf(R.drawable.recording2_3) -> {
                            explanationForEachStep = R.string.step6_connection
                        }
                        images.indexOf(R.drawable.recording2_3) -> {
                            explanationForEachStep = R.string.step7_connection
                        }
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
                showDialog.value = true
            }
            else -> {
                buttonText = stringResource(id = R.string.next_step)
            }
        }
    }

    if (showDialog.value) {
        AlertDialog(
            backgroundColor = Color.White,
            shape = RoundedCornerShape(16.dp),
            onDismissRequest = {
                showDialog.value = true
            },
            title = {
                Text(
                    text = "Congratulations!",
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Text(
                    text = "When you press the button you will be send to the home page"
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        showDialog.value = false
                        navController.navigate(Screen.Home.route)
                    },
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                ) {
                    Text(text = "Next step")
                }
            }
        )
    }
}

fun imageForEachStepConnection(currentStep: Int): List<Int> {
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
        3 -> {
            listOf(
                R.drawable.recording2_1,
                R.drawable.recording2_2,
                R.drawable.recording2_3
            )
        }
        4 -> {
            listOf(
                R.drawable.recording2_1,
                R.drawable.recording2_2,
                R.drawable.recording2_3
            )
        }
        5 -> {
            listOf(
                R.drawable.recording2_1,
                R.drawable.recording2_2,
                R.drawable.recording2_3
            )
        }
        6 -> {
            listOf(
                R.drawable.recording2_1,
                R.drawable.recording2_2,
                R.drawable.recording2_3
            )
        }
        7 -> {
            listOf(
                R.drawable.recording2_1,
                R.drawable.recording2_2,
                R.drawable.recording2_3
            )
        }
        else -> {
            listOf()
        }
    }
}

/**
 * @author Kaan Uğur
 * @author Casey Kruijer
 * @author Redouan Bouziza
 */
fun currentStepExplanationConnection(currentStep: Int): Int {
    return when (currentStep) {
        1 -> R.string.step1_connection
        2 -> R.string.step2_connection
        3 -> R.string.step3_connection
        4 -> R.string.step4_connection
        5 -> R.string.step5_connection
        6 -> R.string.step6_connection
        7 -> R.string.step7_connection
        else -> R.string.no_explanation
    }
}