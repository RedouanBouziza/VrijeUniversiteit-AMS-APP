package com.example.vu.ui.screens

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import com.example.vu.R
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.example.vu.ui.screens.menu.MenuBody
import com.example.vu.ui.screens.menu.MenuHeader
import com.example.vu.ui.screens.menu.MenuItem
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun Home(navController: NavHostController) {
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
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
                    when(it.id) {
                        "home" -> navController.navigate(Screen.Home.route)
                        "faq" -> navController.navigate(Screen.Setup.route)
                        "chart" -> navController.navigate(Screen.Chart.route)
                    }
                }
            )
        }
    ) {
        Text(text = "Home Page")
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




