package com.example.vu.ui.screens.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.vu.R
import com.example.vu.data.viewmodel.ChartViewModel
import com.example.vu.ui.screens.chart.ChartTypeHome

@Composable
fun HomeConnected(
    modifier: Modifier,
    chartViewModel: ChartViewModel
) {
    val stringResource = "Cigarette"

    val MARKER = "cmd !MARKER=$stringResource;"

    LazyColumn(content = {
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
                    Column() {
                        Box(
                            modifier
                                .fillMaxWidth()
                                .height(150.dp)
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
                                .height(150.dp)
                                .clip(
                                    RoundedCornerShape(
                                        bottomEnd = 10.dp,
                                        bottomStart = 10.dp
                                    )
                                )
                        ) {
                            ChartTypeHome(chartViewModel, lineName = "ICG")
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
//                                    webSocket.sendMessage(MARKER)
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
    })
}
