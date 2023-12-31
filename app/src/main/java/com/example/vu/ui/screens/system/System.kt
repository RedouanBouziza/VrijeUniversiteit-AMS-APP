package com.example.vu.ui.screens.system

import android.content.Intent
import android.provider.Settings.*
import android.view.View
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
fun System(modifier: Modifier, navController: NavHostController, webSocket: SocketService) {

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = stringResource(id = R.string.title_settings),
            modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 20.dp),
            style = MaterialTheme.typography.h4,
            fontSize = 32.sp,
            color = colorResource(id = R.color.amsDark)
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = {
            // Wireless settings screen.
            val i = Intent(ACTION_WIRELESS_SETTINGS)
            context.startActivity(i)
        }, modifier = Modifier.width(300.dp)) {

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

        //TODO: uncomment when bluetooth is implemented
        /*Button(onClick = {
            val i = Intent(ACTION_BLUETOOTH_SETTINGS)
            context.startActivity(i)
        }, modifier = Modifier.width(300.dp)) {
            Text(text = "Open Bluetooth Settings")
        }

        Spacer(modifier = Modifier.height(10.dp))*/

        Button(onClick = {
            // Location settings screen.
            val i = Intent(ACTION_LOCATION_SOURCE_SETTINGS)
            context.startActivity(i)
        }, modifier = Modifier.width(300.dp)) {
            // on below line adding a text for our button.
            Text(text = "Open Location Settings", color = Color.White)
        }

        Spacer(modifier = Modifier.height(50.dp))

        Text(
            text = stringResource(id = R.string.title_device),
            modifier.align(Alignment.CenterHorizontally),
            style = MaterialTheme.typography.h4,
            fontSize = 32.sp,
            color = colorResource(id = R.color.amsDark)
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = {
            webSocket.sendMessage(MEASUREMENT_START)
            webSocket.sendMessage(LIVE_DATA_START)
            Toast.makeText(context, R.string.start_receiving_data, Toast.LENGTH_SHORT).show()
        }, modifier = Modifier.width(300.dp)) {
            // on below line adding a text for our button.
            Text(stringResource(R.string.start_device))
        }

        Spacer(modifier = Modifier.height(10.dp))

        Button(onClick = {
            webSocket.sendMessage(MEASUREMENT_STOP)
            Toast.makeText(context, R.string.stopped_measuring, Toast.LENGTH_SHORT).show()
        }, modifier = Modifier.width(300.dp)) {
            Text(text = "Stop Measuring")
        }

        Spacer(modifier = Modifier.height(10.dp))

        Button(onClick = {
            webSocket.sendMessage(SHUT_DOWN_DEVICE)
            Toast.makeText(context, R.string.shutdown_device, Toast.LENGTH_SHORT).show()
        }, modifier = Modifier.width(300.dp)) {
            Text(text = "Shutdown Device")
        }
    }
}