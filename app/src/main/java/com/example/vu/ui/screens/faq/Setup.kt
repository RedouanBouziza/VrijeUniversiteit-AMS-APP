package com.example.vu.ui.screens.faq

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
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
import com.google.android.material.math.MathUtils.lerp
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

/**
 * @author Kaan Uğur
 * @author Casey Kruijer
 * @author Redouan Bouziza
 */
@OptIn(ExperimentalPagerApi::class)
@Composable
fun SetupInstructions(navController: NavHostController) {
    var currentStep by remember { mutableStateOf(1) }
    var buttonText by remember { mutableStateOf("") }
    var explanationForEachStep by remember { mutableStateOf(currentStepExplanation(currentStep)) }
    val images = imageForEachStep(currentStep)
    val pagerState = rememberPagerState(initialPage = images.size)
    val scope = rememberCoroutineScope()

    val secondLastQuestion = 6
    val lastQuestion = 7


    Column(
        Modifier
            .padding(horizontal = 15.dp)
            .padding(top = 15.dp)
    ) {
        Text(
            text = stringResource(id = R.string.title_setup),
            style = MaterialTheme.typography.h5
        )

        Text(
            text = stringResource(id = R.string.step, currentStep),
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
                        lerp(0.85f, 1f, 1f - pageOffset.coerceIn(0f, 1f)).also { scale ->
                            scaleX = scale
                            scaleY = scale
                        }

                        // We animate the alpha, between 50% and 100%
                        alpha = lerp(0.5f, 1f, 1f - pageOffset.coerceIn(0f, 1f))
                    }
            ) {
                Image(
                    painter = painterResource(images[page]),
                    contentDescription = "Steps images",
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier.fillMaxSize()
                )

                SideEffect {
                    when (pagerState.currentPage) {
                        images.indexOf(R.drawable.step5_1) -> {
                            explanationForEachStep = R.string.step5a_explanation
                        }
                        images.indexOf(R.drawable.step5_2) -> {
                            explanationForEachStep = R.string.step5a_explanation
                        }
                        images.indexOf(R.drawable.step5_3) -> {
                            explanationForEachStep = R.string.step5a_explanation
                        }
                        images.indexOf(R.drawable.step5_4) -> {
                            explanationForEachStep = R.string.step5b_explanation
                        }
                        images.indexOf(R.drawable.step5_5) -> {
                            explanationForEachStep = R.string.step5b_explanation
                        }
                        images.indexOf(R.drawable.step5_6) -> {
                            explanationForEachStep = R.string.step5b_explanation
                        }
                        images.indexOf(R.drawable.step5_7) -> {
                            explanationForEachStep = R.string.step5c_explanation
                        }
                        images.indexOf(R.drawable.step5_8) -> {
                            explanationForEachStep = R.string.step5c_explanation
                        }
                        images.indexOf(R.drawable.step5_9) -> {
                            explanationForEachStep = R.string.step5d_explanation
                        }
                        images.indexOf(R.drawable.step5_10) -> {
                            explanationForEachStep = R.string.step5d_explanation
                        }
                        images.indexOf(R.drawable.step6_1) -> {
                            explanationForEachStep = R.string.step6a_explanation
                        }
                        images.indexOf(R.drawable.step6_2) -> {
                            explanationForEachStep = R.string.step6a_explanation
                        }
                        images.indexOf(R.drawable.step6_3) -> {
                            explanationForEachStep = R.string.step6a_explanation
                        }
                        images.indexOf(R.drawable.step6_4) -> {
                            explanationForEachStep = R.string.step6b_explanation
                        }
                    }
                }

            }
        }
    }


    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter,
    ) {
        Button(
            shape = RoundedCornerShape(5.dp),
            onClick = {
                currentStep++
                scope.launch { pagerState.scrollToPage(0) }
            }
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

fun imageForEachStep(currentStep: Int): List<Int> {
    return when (currentStep) {
        1 -> {
            listOf(
                R.drawable.step1
            )
        }
        2 -> {
            listOf(
                R.drawable.step2
            )
        }
        3 -> {
            listOf(
                R.drawable.step3
            )
        }
        4 -> {
            listOf(
                R.drawable.step4
            )

        }
        5 -> {
            listOf(
                R.drawable.step5_1,
                R.drawable.step5_2,
                R.drawable.step5_3,
                R.drawable.step5_4,
                R.drawable.step5_5,
                R.drawable.step5_6,
                R.drawable.step5_7,
                R.drawable.step5_8,
                R.drawable.step5_9,
                R.drawable.step5_10
            )
        }
        else -> {
            listOf(
                R.drawable.step6_1,
                R.drawable.step6_2,
                R.drawable.step6_3,
                R.drawable.step6_4
            )
        }
    }
}

/**
 * @author Kaan Uğur
 * @author Casey Kruijer
 * @author Redouan Bouziza
 */
fun currentStepExplanation(currentStep: Int): Int {
    return when (currentStep) {
        1 -> R.string.step1_explanation
        2 -> R.string.step2_explanation
        3 -> R.string.step3_explanation
        4 -> R.string.step4_explanation
        5 -> R.string.step5a_explanation
        6 -> R.string.step6a_explanation
        else -> R.string.no_explanation
    }
}