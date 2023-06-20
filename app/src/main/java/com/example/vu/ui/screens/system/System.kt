package com.example.vu.ui.screens.system

import android.content.Intent
import android.provider.Settings.*
import android.view.View
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.Group
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.vu.R
import com.example.vu.data.websocket.SocketService

// the commands send to device
const val LIVE_DATA_START = "cmd 3a"
const val MEASUREMENT_START = "cmd r"
const val MEASUREMENT_STOP = "cmd s"
const val SHUT_DOWN_DEVICE = "cmd Q"

@Composable
fun System(navController: NavHostController) {

    val context = LocalContext.current
    val webSocket: SocketService by lazy { SocketService() }
    val isLoggedIn = true
//    val isLoggedIn by loginViewModel.isLoggedIn.collectAsState()

    val measurementGroupVisibility = if (isLoggedIn) View.VISIBLE else View.INVISIBLE
    val shutdownGroupVisibility = if (isLoggedIn) View.VISIBLE else View.INVISIBLE


    DisposableEffect(key1 = webSocket) {
        webSocket.openConnection()
        onDispose {
            webSocket.closeConnection()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = stringResource(id = R.string.title_settings),
            color = colorResource(id = R.color.ams), fontSize = 20.sp, fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = {
            // Wireless settings screen.
            val i = Intent(ACTION_WIRELESS_SETTINGS)
            context.startActivity(i)
        }, modifier = Modifier.width(300.dp))
        {

            Text(text = "Open Wireless Settings")
        }

        Spacer(modifier = Modifier.height(10.dp))

        Button(onClick = {
            // Wifi settings screen.
            val i = Intent(ACTION_WIFI_SETTINGS)
            context.startActivity(i)
        }, modifier = Modifier.width(300.dp)) {
            Text(text = "Wifi Settings")
        }

        Spacer(modifier = Modifier.height(10.dp))

        Button(onClick = {
            val i = Intent(ACTION_BLUETOOTH_SETTINGS)
            context.startActivity(i)
        }, modifier = Modifier.width(300.dp)) {
            Text(text = "Open Bluetooth Settings")
        }

        Spacer(modifier = Modifier.height(10.dp))

        Button(onClick = {
            // Location settings screen.
            val i = Intent(ACTION_LOCATION_SOURCE_SETTINGS)
            context.startActivity(i)
        }, modifier = Modifier.width(300.dp)) {
            // on below line adding a text for our button.
            Text(text = "Open Location Settings", color = Color.White)
        }

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = stringResource(id = R.string.title_device),
            color = colorResource(id = R.color.ams), fontSize = 20.sp, fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(10.dp))

        Button(onClick = {
            webSocket.sendMessage(MEASUREMENT_START)
            webSocket.sendMessage(LIVE_DATA_START)
            Toast.makeText(context, R.string.start_receiving_data, Toast.LENGTH_SHORT).show()
        }, modifier = Modifier.width(300.dp)) {
            // on below line adding a text for our button.
            Text(stringResource(R.string.start_device))
        }

        Spacer(modifier = Modifier.height(10.dp))


        if (isLoggedIn) {
            Button(onClick = {
                webSocket.sendMessage(MEASUREMENT_STOP)
                Toast.makeText(context, R.string.stopped_measuring, Toast.LENGTH_SHORT)
                    .show()
            }, modifier = Modifier.width(300.dp)) {
                Text(text = "Stop Measuring")
            }

            Button(onClick = {
                webSocket.sendMessage(SHUT_DOWN_DEVICE)
                Toast.makeText(context, R.string.shutdown_device, Toast.LENGTH_SHORT)
                    .show()
            }, modifier = Modifier.width(300.dp)) {
                Text(text = "Shutdown Device")
            }
        }
    }
}