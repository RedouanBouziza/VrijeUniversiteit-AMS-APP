package com.example.vu

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.vu.ui.screens.breathing.BreathingSettings
import com.example.vu.ui.screens.Chart
import com.example.vu.ui.screens.Home
import com.example.vu.ui.screens.Screen
import com.example.vu.ui.screens.breathing.BreathingExercise
import com.example.vu.ui.screens.faq.SetupInstructions
import com.example.vu.ui.screens.menu.MenuBody
import com.example.vu.ui.screens.menu.MenuHeader
import com.example.vu.ui.theme.VUTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * @author Casey Kruijer
 * @author Kaan Uğur
 * @author Redouan Bouziza
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent()
    }

    private fun setContent() {
        setContent {
            VUTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    ScreenContent(modifier = Modifier)
                }
            }
        }
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
private fun ScreenContent(modifier: Modifier) {
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val navController = rememberNavController()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopBar(onNavigationIconClick = {
                scope.launch { scaffoldState.drawerState.open() }
            })
        },
        drawerGesturesEnabled = scaffoldState.drawerState.isOpen,
        drawerContent = {
            MenuHeader()
            MenuBody(onItemClick = {
                when (it.id) {
                    "home" -> {
                        navController.navigate(Screen.Home.route)
                        closeNavBar(scope, scaffoldState)
                    }
                    "faq" -> {
                        navController.navigate(Screen.Setup.route)
                        closeNavBar(scope, scaffoldState)
                    }
                    "chart" -> {
                        navController.navigate(Screen.Chart.route)
                        closeNavBar(scope, scaffoldState)
                    }
                    "breathing" -> {
                        navController.navigate(Screen.BreathingSettings.route)
                        closeNavBar(scope, scaffoldState)
                    }
                }
            }
            )
        }
    ) {
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = modifier
        ) {
            composable(route = Screen.Home.route) {
                Home()
            }
            composable(route = Screen.Chart.route) {
                Chart(navController)
            }
            composable(route = Screen.BreathingSettings.route) {
                BreathingSettings(navController)
            }
            composable(route = Screen.BreathingExercise.route) {
                BreathingExercise()
            }
            composable(route = Screen.Setup.route) {
                SetupInstructions(navController = navController)
            }
        }
    }
}

@Composable
fun TopBar(
    onNavigationIconClick: () -> Unit
) {
    TopAppBar(
        backgroundColor = colorResource(id = R.color.ams),
        contentColor = Color.White,
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.ams),
                )
            }
        },
        navigationIcon = {
            IconButton(
                onClick =
                onNavigationIconClick
            ) {
                Icon(
                    Icons.Default.Menu,
                    contentDescription = "Toggle menu",
                )
            }
        },
        actions = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    Icons.Default.AccountBox,
                    contentDescription = "AccountBox",
                )
            }
        }
    )
}

fun closeNavBar(scope: CoroutineScope, scaffoldState: ScaffoldState) {
    scope.launch { scaffoldState.drawerState.close() }
}