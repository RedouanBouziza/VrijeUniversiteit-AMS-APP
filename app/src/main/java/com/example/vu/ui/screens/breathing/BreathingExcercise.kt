package com.example.vu.ui.screens.breathing

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.vu.R
import com.example.vu.data.viewmodel.BreathingViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

enum class ImageState {
    Start,
    End,
}

@Composable
fun BreathingExercise(scope: CoroutineScope, breathingViewModel: BreathingViewModel) {
    var currentState by remember { mutableStateOf(ImageState.Start) }
    val transition = updateTransition(currentState, "image state")

    val breathIn = breathingViewModel.breathIn.value!! * 1000
    val breathOut = breathingViewModel.breathOut.value!! * 1000
    val pause = breathingViewModel.pause.value!! * 1000

    val imageSize by transition.animateFloat(
        transitionSpec = {
            when {
                ImageState.Start isTransitioningTo ImageState.End ->
                    tween(breathIn, pause, LinearEasing)
                else ->
                    tween(breathOut, 0, LinearEasing)
            }
        }, label = ""
    ) { state ->
        when (state) {
            ImageState.Start -> 100.0f
            ImageState.End -> 250.0f
        }
    }

   LaunchedEffect(
       key1 = Unit,
       block = {
           scope.launch {
               currentState = switchState(currentState)
           }
       },
   )

//    val infiniteTransition = rememberInfiniteTransition()
//    val imageSize by infiniteTransition.animateFloat(
//        initialValue = 100.0f,
//        targetValue =250.0f ,
//        animationSpec = infiniteRepeatable(
//            tween(breathIn, pause, LinearEasing),
//            RepeatMode.Reverse
//        )
//    )

    Column(
        Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .padding(15.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(R.drawable.breathe),
            contentDescription = "",
            modifier = Modifier
                .size(imageSize.dp)
        )
    }
}

fun switchState(currentState: ImageState): ImageState {
    val state: ImageState
    if (currentState == ImageState.Start) {
        state = ImageState.End
    } else {
        state = ImageState.Start
    }
    return state
}
