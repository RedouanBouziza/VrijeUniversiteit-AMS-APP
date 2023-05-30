package com.example.vu.ui.screens.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.vu.R
import com.example.vu.data.viewmodel.ChartViewModel
import com.example.vu.data.viewmodel.UDPViewModel
import com.example.vu.ui.screens.Screen
import com.example.vu.ui.screens.chart.AllCharts
import com.example.vu.ui.screens.chart.Chart

@Composable
fun Home(
    modifier: Modifier,
    navController: NavHostController,
    chartViewModel: ChartViewModel,
    udpViewModel: UDPViewModel
) {9

    ConnectionEstablished(
        udpViewModel = udpViewModel,
        modifier = modifier,
        chartViewModel = chartViewModel,
        navController = navController
    )


}

@Composable
private fun ConnectionEstablished(
    udpViewModel: UDPViewModel,
    modifier: Modifier,
    chartViewModel: ChartViewModel,
    navController: NavHostController
) {
    val isConnected by udpViewModel.isConnected.observeAsState()
    val isReceivingData by udpViewModel.isReceivingData.observeAsState()


    when (isConnected) {
        true -> {
            Column(
                modifier
                    .padding(18.dp)
                    .fillMaxSize()
            ) {
                Column(
                    modifier = modifier
                        .fillMaxSize()
                ) {
                    Column(
                        modifier
                            .padding(top = 20.dp)
                    ) {
                        Box(
                            modifier
                                .fillMaxWidth()
                                .height(150.dp)
                                .clip(RoundedCornerShape(topEnd = 10.dp, topStart = 10.dp))
                        ) {
                            AllCharts(chartViewModel)
                        }
                        Box(
                            modifier
                                .fillMaxWidth()
                                .height(150.dp)
                                .clip(
                                    RoundedCornerShape(
                                        bottomEnd = 10.dp,
                                        bottomStart = 10.dp
                                    )
                                )
                        ) {
                            AllCharts(chartViewModel)
                        }
                    }

                    Text(
                        text = "TIMESTAMPS",
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
                        ) {
                            Button(
                                onClick = {
                                    //TODO: Stamp the time and the message
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
                                    text = "Cigarette",
                                    color = Color.White,
                                    fontStyle = FontStyle.Italic,
                                )
                            }

                            Button(
                                onClick = {
                                    //TODO: Stamp the time and the message
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
                                    text = "Intense Activity",
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
                                    //TODO: Stamp the time and the message
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
                                    text = "Relaxing",
                                    color = Color.White,
                                    fontStyle = FontStyle.Italic,
                                )
                            }

                            Button(
                                onClick = {
                                    //TODO: Stamp the time and the message
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
                                    text = "Cycling",
                                    color = Color.White,
                                    fontStyle = FontStyle.Italic,
                                )
                            }
                        }
                        Button(
                            onClick = {
                                //TODO: Stamp the time and the message
                            },
                            border = BorderStroke(1.dp, colorResource(id = R.color.amsDark)),
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = colorResource(id = R.color.darkBlue)
                            ),
                            modifier = Modifier
                                .width(372.dp)
                                .height(70.dp)
                                .padding(top = 10.dp),
                        ) {
                            Text(
                                text = "Free Text Marker",
                                color = Color.White,
                                fontStyle = FontStyle.Italic,
                            )
                        }
                    }
                }
            }
        }
        else -> {
            Column(
                modifier
                    .padding(18.dp)
                    .fillMaxSize()
            ) {
                Column(
                    modifier = modifier
                        .fillMaxSize()
                ) {
                    Text(
                        text = "WELCOME",
                        style = MaterialTheme.typography.h3,
                        fontWeight = FontWeight.Bold,
                        fontStyle = FontStyle.Italic,
                        color = colorResource(id = R.color.amsDark)
                    )

                    Image(
                        painter = painterResource(id = R.drawable.logo),
                        contentDescription = "Logo",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 15.dp)
                            .clip(RoundedCornerShape(10.dp))
                    )

                    //First button
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 20.dp)
                            .height(90.dp),
                        onClick = {
                            navController.navigate("Setup")
                        },
                        border = BorderStroke(1.dp, colorResource(id = R.color.amsDark)),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = colorResource(id = R.color.ams)
                        ),
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                modifier = Modifier
                                    .size(50.dp),
                                imageVector = Icons.Default.Filter1,
                                contentDescription = "Help",
                                tint = Color.White
                            )
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(10.dp)
                                    .absolutePadding(left = 10.dp),
                            ) {
                                Text(
                                    text = "Take your first step",
                                    style = MaterialTheme.typography.h5,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                                Text(
                                    text = "Find your device",
                                    style = MaterialTheme.typography.body1,
                                    color = Color.White
                                )
                            }
                        }

                    }

                    //Second button
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 20.dp)
                            .height(90.dp),
                        onClick = {
                            navController.navigate(Screen.StartRecording.route)
                        },
                        border = BorderStroke(1.dp, colorResource(id = R.color.amsDark)),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = colorResource(id = R.color.ams)
                        ),
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                modifier = Modifier
                                    .size(50.dp),
                                imageVector = Icons.Default.Filter2,
                                contentDescription = "Help",
                                tint = Color.White
                            )
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(10.dp)
                                    .absolutePadding(left = 10.dp),
                            ) {
                                Text(
                                    text = "Take your second step",
                                    style = MaterialTheme.typography.h5,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                                Text(
                                    text = "Find the device",
                                    style = MaterialTheme.typography.body1,
                                    color = Color.White
                                )
                            }
                        }

                    }

                }
            }
        }
    }
}
