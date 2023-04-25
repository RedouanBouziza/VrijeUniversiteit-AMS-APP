package com.example.vu

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.vu.data.udp.UDPConnection
import com.example.vu.data.viewmodel.BreathingViewModel
import com.example.vu.data.viewmodel.UDPViewModel
import com.example.vu.ui.screens.Screen
import com.example.vu.ui.screens.breathing.BreathingExercise
import com.example.vu.ui.screens.breathing.BreathingSettings
import com.example.vu.ui.screens.chart.Chart
import com.example.vu.ui.screens.faq.Faq
import com.example.vu.ui.screens.faq.SetupInstructions
import com.example.vu.ui.screens.home.Home
import com.example.vu.ui.screens.menu.MenuBody
import com.example.vu.ui.screens.menu.MenuHeader
import com.example.vu.ui.screens.movement.Movement
import com.example.vu.ui.screens.system.System
import com.example.vu.ui.theme.VUTheme
import com.scichart.charting.visuals.SciChartSurface
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

/**
 * @author Casey Kruijer
 * @author Kaan Uğur
 * @author Redouan Bouziza
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        try {
            SciChartSurface.setRuntimeLicenseKey(BuildConfig.SCI_CHART_KEY)
        } catch (e: Exception) {
            Log.e("SciChart", e.toString())
        }

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
                    val scope = rememberCoroutineScope()

                    MyComposableFunction(scope)
                    ScreenContent(Modifier, scope)
                }
                
            }
        }
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
private fun ScreenContent(modifier: Modifier, scope: CoroutineScope) {
    val scaffoldState = rememberScaffoldState()
    val navController = rememberNavController()
    val breathingViewModel: BreathingViewModel = viewModel()

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
                        navController.navigate(Screen.Faq.route)
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
                    "movement" -> {
                        navController.navigate(Screen.Movement.route)
                        closeNavBar(scope, scaffoldState)
                    }
                    "system" -> {
                        navController.navigate(Screen.System.route)
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
                Home(modifier, navController)
            }
            composable(route = Screen.Chart.route) {
                Chart(navController)
            }
            composable(route = Screen.BreathingSettings.route) {
                BreathingSettings(navController, breathingViewModel, scope)
            }
            composable(Screen.BreathingExercise.route) {
                BreathingExercise(breathingViewModel, scope)
            }
            composable(route = Screen.Setup.route) {
                SetupInstructions(navController)
            }
            composable(route = Screen.Movement.route) {
                Movement(navController)
            }
            composable(route = Screen.Faq.route) {
                Faq(navController)
            }
            composable(route = Screen.System.route) {
                System(navController)
            }
        }
    }
}

@Composable
private fun TopBar(onNavigationIconClick: () -> Unit) {
    val udpViewModel: UDPViewModel = viewModel()

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
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 13.dp),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically,
            ) {

                ConnectionEstablished(udpViewModel)
            }
        }
    )
}

//TODO: Check if this works with the new data
@Composable
private fun ConnectionEstablished(udpViewModel: UDPViewModel) {
    val isConnected by udpViewModel.isConnected.observeAsState()
    val isReceivingData by udpViewModel.isReceivingData.observeAsState()

    udpViewModel.isConnected.observeAsState()

    when (isConnected) {
        true -> {
            Icon(
                imageVector = Icons.Default.Wifi,
                contentDescription = "Wifi",
                tint = Color.White
            )
            if (!isReceivingData!!) {
                Icon(
                    imageVector = Icons.Default.Wifi,
                    contentDescription = "Wifi",
                    tint = Color.White
                )
                if (!isReceivingData!!) {
                    Icon(
                        imageVector = Icons.Default.WifiProtectedSetup,
                        contentDescription = "WifiProtectedSetup",
                        tint = Color.White
                    )
                }
            }
            else -> {
                Icon(
                    imageVector = Icons.Default.WifiOff,
                    contentDescription = "WifiOff",
                    tint = Color.White
                )
            }
        }
}

private fun closeNavBar(scope: CoroutineScope, scaffoldState: ScaffoldState) {
    scope.launch { scaffoldState.drawerState.close() }
}

//TODO: Check if this works with the new data
@Composable
fun MyComposableFunction(scope: CoroutineScope) {
    val udpViewModel: UDPViewModel = viewModel()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        scope.launch(Dispatchers.Main) {
            UDPConnection(
                context = context,
                3,
                3,
                setConnectedCallBack = { isConnected, isReceivingData ->
                    udpViewModel.setIsReceivingData(isReceivingData)
                    udpViewModel.setIsConnected(isConnected)
                }
            )
        }
    }

}

