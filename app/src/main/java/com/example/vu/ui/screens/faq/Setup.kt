package com.example.vu.ui.screens.faq

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.vu.R
import com.example.vu.ui.screens.Screen
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import com.google.android.material.math.MathUtils.lerp
import kotlin.math.absoluteValue

/**
 * @author Kaan Uğur
 * @author Casey Kruijer
 * @author Redouan Bouziza
 */
@Composable
fun SetupInstructions(navController: NavHostController) {
    var currentStep by remember { mutableStateOf(1) }
    var buttonText by remember { mutableStateOf("") }
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
            Modifier.padding(top = 50.dp),
            style = MaterialTheme.typography.subtitle1
        )

        Text(
            text = stringResource(id = currentStepExplanation(currentStep)),
            Modifier.padding(top = 50.dp),
            style = MaterialTheme.typography.subtitle2
        )

//        Image(
//            painter = painterResource(id = imageForEachStep(currentStep)),
//            modifier = Modifier.fillMaxSize(),
//            contentDescription = "Instruction image"
//        )
        ImageSlideshow(currentImage = 6)
    }


    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter,
    ) {
        Button(
            shape = RoundedCornerShape(5.dp),
            onClick = { currentStep++ }
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

/**
 * @author Kaan Uğur
 * @author Casey Kruijer
 * @author Redouan Bouziza
 */
fun currentStepImage(currentStep: Int): Int {
    return when (currentStep) {
        1 -> R.drawable.eggman
        2 -> R.drawable.ams
        3 -> R.drawable.eggman
        else -> R.drawable.eggman
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

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ImageSlideshow(currentImage: Int) {
    val image = imageForEachStep(currentImage)
    // Display 10 items
//    HorizontalPager(count = image.size) { page ->
//        // Our page content
//        Image(
//            painter = painterResource(id = R.drawable.step1),
//            modifier = Modifier.fillMaxSize(),
//            contentDescription = "Instruction image"
//        )
//
//        Text(
//            text = "Page: $page",
//            modifier = Modifier.fillMaxWidth()
//        )
//    }

    HorizontalPager(count = image.size) { page ->
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
                painter = painterResource(R.drawable.step1),
                contentDescription = "Steps images",
                contentScale = ContentScale.FillWidth,
                modifier = Modifier.fillMaxSize()
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
        5 -> R.string.step5_explanation
        else -> R.string.step6_explanation
    }
}