package com.example.vu

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.vu.data.udp.UDPConnection
import com.example.vu.data.viewmodel.BreathingViewModel
import com.example.vu.data.viewmodel.ChartViewModel
import com.example.vu.data.viewmodel.UDPViewModel
import com.example.vu.ui.screens.Screen
import com.example.vu.ui.screens.breathing.BreathingExercise
import com.example.vu.ui.screens.breathing.BreathingSettings
import com.example.vu.ui.screens.chart.Chart
import com.example.vu.ui.screens.home.Home
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.vu.data.websocket.SocketService
import com.example.vu.ui.screens.about.AboutUs
import com.example.vu.ui.screens.faq.*
import com.example.vu.ui.screens.home.HomeConnected
import com.example.vu.ui.screens.movement.Movement
import com.example.vu.ui.screens.setup.SetupInstructions
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
    private lateinit var locationPermissionLauncher: ActivityResultLauncher<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        locationPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                // Permission granted, handle location logic
                setContent()
            } else {
                // Permission denied, handle accordingly
                Log.d("MainActivity", "Location permission denied")
            }
        }

        requestLocationPermission()

        try {
            SciChartSurface.setRuntimeLicenseKey(BuildConfig.SCI_CHART_KEY)
        } catch (e: Exception) {
            Log.e("SciChart", e.toString())
        }
    }

    private fun requestLocationPermission() {
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) ==
            PackageManager.PERMISSION_GRANTED
        ) {
            // Permission already granted
            setContent {
                setContent()
            }
        } else {
            // Request location permission
            locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    private fun setContent() {
        setContent {
            VUTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MyComposableFunction()
                    ScreenContent(Modifier)
                }
            }
        }
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
private fun ScreenContent(modifier: Modifier) {
    val webSocket: SocketService by lazy { SocketService() }

    DisposableEffect(key1 = webSocket) {
        webSocket.openConnection()
        onDispose {
            webSocket.closeConnection()
        }
    }
    val scaffoldState = rememberScaffoldState()
    val navController = rememberNavController()
    val breathingViewModel: BreathingViewModel = viewModel()
    val chartViewModel: ChartViewModel = viewModel()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopBar(navController = navController)
        },
        bottomBar = {
            BottomBar(navController = navController)
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
            composable(route = Screen.HomeConnected.route) {
                HomeConnected(modifier, chartViewModel, webSocket)
            }
            composable(route = Screen.Chart.route) {
                Chart(chartViewModel)
            }
            composable(route = Screen.BreathingSettings.route) {
                BreathingSettings(navController, breathingViewModel)
            }
            composable(Screen.BreathingExercise.route) {
                BreathingExercise(breathingViewModel, chartViewModel)
            }
            composable(route = Screen.Setup.route) {
                SetupInstructions(navController)
            }
            composable(route = Screen.Movement.route) {
                Movement(navController)
            }
            composable(route = Screen.Faq.route) {
                Faq()
            }
            composable(route = Screen.System.route) {
                System(modifier, navController, webSocket)
            }
            composable(route = Screen.SetupConnection.route){
                SetupConnection(navController)
            }
            composable(route = Screen.AboutUs.route){
                AboutUs()
            }
        }
    }
}

@Composable
private fun TopBar(
    navController: NavHostController
) {
    val udpViewModel: UDPViewModel = viewModel()
    val context = LocalContext.current
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    TopAppBar(
        backgroundColor = colorResource(id = R.color.ams),
        contentColor = Color.White,
        navigationIcon = {
            if (currentDestination?.route == (Screen.HomeConnected.route)
            ) {
                IconButton(
                    onClick = {
                        navController.navigateUp()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            }
        },
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "",
                )
            }
        },
        actions = {
            IconButton(
                onClick = {
                    // Wifi settings screen.
                    val i = Intent(Settings.ACTION_WIFI_SETTINGS)
                    context.startActivity(i)
                }
            ) {
                ConnectionEstablished(udpViewModel)
            }
        }
    )
}

@Composable
private fun ConnectionEstablished(udpViewModel: UDPViewModel) {
    val isConnected by udpViewModel.isConnected.observeAsState()
    val isReceivingData by udpViewModel.isReceivingData.observeAsState()

    when {
        isConnected == true -> {
            Icon(
                imageVector = Icons.Default.Wifi,
                contentDescription = "Wifi",
                tint = Color.White
            )
        }
        isReceivingData == true && isConnected == false -> {
            Icon(
                imageVector = Icons.Default.WifiProtectedSetup,
                contentDescription = "AnotherIcon",
                tint = Color.White
            )
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

@Composable
private fun BottomBar(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val homeScreens = listOf(
        Screen.Measurement,
        Screen.AboutUs,
        Screen.Faq,
        Screen.System
    )
    val homeScreensWithHomeButton = listOf(
        Screen.HomeConnected,
        Screen.Measurement,
        Screen.AboutUs,
        Screen.Faq,
        Screen.System
    )
    val secondScreens = listOf(
        Screen.HomeConnected,
        Screen.Chart,
        Screen.BreathingSettings,
        Screen.Movement
    )

    when (currentDestination?.route) {
        (Screen.HomeConnected.route) -> {
            BottomBarItems(navController, homeScreens.filterNot { it.route == currentDestination.route })
        }
        in listOf(
            Screen.Faq.route,
            Screen.System.route,
            Screen.AboutUs.route
        ) -> {
            if (currentDestination != null) {
                BottomBarItems(navController, homeScreensWithHomeButton.filterNot { it.route == currentDestination.route })
            }
        }
        !in listOf(
            Screen.Home.route,
            Screen.Setup.route,
            Screen.SetupConnection.route
        ) -> {
            if (currentDestination != null) {
                BottomBarItems(navController, secondScreens.filterNot { it.route == currentDestination.route })
            }
        }
    }
}


@Composable
private fun BottomBarItems(navController: NavController, screens: List<Screen>) {
    BottomNavigation {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        screens.forEach { screen ->
            BottomNavigationItem(
                icon = { Icon(screen.icon, contentDescription = null) },
                label = { Text(stringResource(id = screen.titleId)) },
                selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                unselectedContentColor = Color.White,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.findStartDestination().id)
                        launchSingleTop = true
                    }
                })
        }
    }
}



@Composable
fun MyComposableFunction() {
    val udpViewModel: UDPViewModel = viewModel()
    val chartViewModel: ChartViewModel = viewModel()
    val context = LocalContext.current

    Thread(
        UDPConnection(
            context = context,
            3,
            3,
            setConnectedCallBack = { isConnected, isReceivingData ->
                // Update the view model on the main thread
                CoroutineScope(Dispatchers.Main).launch {
                    udpViewModel.setIsReceivingData(isReceivingData)
                    udpViewModel.setIsConnected(isConnected)
                }
            },
            setASectionMeasurement = {
                CoroutineScope(Dispatchers.Main).launch {
                    chartViewModel.setASectionMeasurement(TreeMap(it))

                }
            })
    ).start()
}

