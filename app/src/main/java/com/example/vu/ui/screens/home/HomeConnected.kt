package com.example.vu.ui.screens.home

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.vu.MyComposableFunction
import com.example.vu.R
import com.example.vu.data.viewmodel.ChartViewModel
import com.example.vu.data.websocket.SocketService
import com.example.vu.ui.screens.chart.ChartTypeHome

@Composable
fun HomeConnected(
    modifier: Modifier,
    chartViewModel: ChartViewModel,
    webSocket: SocketService
) {
    val customMarkerText = remember { mutableStateOf("") }

    val marker = "cmd !MARKER=${customMarkerText.value};"

    val cigarette = "Cigarette"
    val intenseActivity = "Intense Activity"
    val relaxing = "Relaxing"
    val cycling = "Cycling"

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 55.dp)
    ) {
        item {
            Column(
                modifier
                    .padding(18.dp)
                    .fillMaxSize()
            ) {
                Column(
                    modifier = modifier
                        .fillMaxSize()
                ) {
                    Column {
                        // Call your desired composable function or perform any action here
                        Box(
                            modifier
                                .fillMaxWidth()
                                .height(190.dp)
                                .clip(
                                    RoundedCornerShape(
                                        topEnd = 10.dp,
                                        topStart = 10.dp
                                    )
                                )
                        ) {
                            ChartTypeHome(chartViewModel, lineName = "ECG")
                        }

                        Box(
                            modifier
                                .fillMaxWidth()
                                .height(190.dp)
                                .clip(
                                    RoundedCornerShape(
                                        bottomEnd = 10.dp,
                                        bottomStart = 10.dp
                                    )
                                )
                        ) {
                            ChartTypeHome(chartViewModel, lineName = "RES")
                        }
                    }

                    Text(
                        text = stringResource(id = R.string.MARKERS),
                        style = MaterialTheme.typography.h4,
                        fontWeight = FontWeight.Bold,
                        color = colorResource(id = R.color.amsDark),
                        modifier = Modifier.padding(top = 20.dp)
                    )

                    Column(
                        modifier
                            .padding(top = 16.dp),
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Button(
                                onClick = {
                                    webSocket.sendMessage("cmd !MARKER=${cigarette};")
                                },
                                border = BorderStroke(
                                    1.dp,
                                    colorResource(id = R.color.amsDark)
                                ),
                                shape = RoundedCornerShape(10.dp),
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = colorResource(id = R.color.babyBlue)
                                ),
                                modifier = Modifier
                                    .width(181.dp)
                                    .height(60.dp),
                            ) {
                                Text(
                                    text = stringResource(id = R.string.Cigarette),
                                    color = Color.White,
                                    fontStyle = FontStyle.Italic,
                                )
                            }

                            Button(
                                onClick = {
                                    webSocket.sendMessage("cmd !MARKER=${intenseActivity};")
                                },
                                border = BorderStroke(
                                    1.dp,
                                    colorResource(id = R.color.amsDark)
                                ),
                                shape = RoundedCornerShape(10.dp),
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = colorResource(id = R.color.pink)
                                ),
                                modifier = Modifier
                                    .width(181.dp)
                                    .height(60.dp),
                            ) {
                                Text(
                                    text = stringResource(id = R.string.Intense_Activity),
                                    color = Color.White,
                                    fontStyle = FontStyle.Italic,
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Button(
                                onClick = {
                                    webSocket.sendMessage("cmd !MARKER=${relaxing};")
                                },
                                border = BorderStroke(
                                    1.dp,
                                    colorResource(id = R.color.amsDark)
                                ),
                                shape = RoundedCornerShape(10.dp),
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = colorResource(id = R.color.blue)
                                ),
                                modifier = Modifier
                                    .width(181.dp)
                                    .height(60.dp),
                            ) {
                                Text(
                                    text = stringResource(id = R.string.Relaxing),
                                    color = Color.White,
                                    fontStyle = FontStyle.Italic,
                                )
                            }

                            Button(
                                onClick = {
                                    webSocket.sendMessage("cmd !MARKER=${cycling};")
                                },
                                border = BorderStroke(
                                    1.dp,
                                    colorResource(id = R.color.amsDark)
                                ),
                                shape = RoundedCornerShape(10.dp),
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = colorResource(id = R.color.purple)
                                ),
                                modifier = Modifier
                                    .width(181.dp)
                                    .height(60.dp),
                            ) {
                                Text(
                                    text = stringResource(id = R.string.Cycling),
                                    color = Color.White,
                                    fontStyle = FontStyle.Italic,
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(130.dp)
                        ) {
                            TextField(
                                value = customMarkerText.value,
                                onValueChange = { customMarkerText.value = it },
                                label = { Text("Enter custom marker") },
                                modifier = Modifier
                                    .weight(1f)
                                    .height(60.dp)
                                    .fillMaxWidth()
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            Button(
                                onClick = {
                                    webSocket.sendMessage(marker)
                                },
                                border = BorderStroke(
                                    1.dp,
                                    colorResource(id = R.color.amsDark)
                                ),
                                shape = RoundedCornerShape(10.dp),
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = colorResource(id = R.color.darkBlue)
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(60.dp),
                            ) {
                                Text(
                                    text = "Custom",
                                    color = Color.White,
                                    fontStyle = FontStyle.Italic,
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}