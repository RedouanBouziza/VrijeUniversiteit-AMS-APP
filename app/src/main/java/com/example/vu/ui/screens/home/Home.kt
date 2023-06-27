package com.example.vu.ui.screens.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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

@Composable
fun Home(
    modifier: Modifier,
    navController: NavHostController,
) {
    LazyColumn(content = {
        item {
            Column(
                modifier
                    .padding(18.dp)
                    .fillMaxSize()
            ) {

                Column(
                    modifier
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
                                    text = "First step",
                                    style = MaterialTheme.typography.h5,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                                Text(
                                    text = "Wear the device",
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
                            navController.navigate(Screen.SetupConnection.route)
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
                                    text = "Second step",
                                    style = MaterialTheme.typography.h5,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                                Text(
                                    text = "Power on the device",
                                    style = MaterialTheme.typography.body1,
                                    color = Color.White
                                )
                            }
                        }

                    }

                    //Third button
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 20.dp)
                            .height(90.dp),
                        onClick = {
                            navController.navigate(Screen.HomeConnected.route)
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
                                imageVector = Icons.Default.Filter3,
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
                                    text = "Final step",
                                    style = MaterialTheme.typography.h5,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                                Text(
                                    text = "Use the device",
                                    style = MaterialTheme.typography.body1,
                                    color = Color.White
                                )
                            }
                        }

                    }

                }
            }
        }
    })

}
