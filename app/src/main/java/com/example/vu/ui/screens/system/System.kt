package com.example.vu.ui.screens.system

import android.content.Context
import android.content.Intent
import android.provider.Settings.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.vu.R

@Composable
fun System(navController: NavHostController) {

    val context = LocalContext.current

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
            // TODO write the logic here

        }, modifier = Modifier.width(300.dp)) {
            Text(text = "Start Device")
        }

        Spacer(modifier = Modifier.height(10.dp))

        Button(onClick = {
            // TODO write logic here
        }, modifier = Modifier.width(300.dp)) {
            Text(text = "Stop Device" )
        }

    }
}