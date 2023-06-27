package com.example.vu.ui.screens.faq

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.vu.R

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun Faq() {
    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxWidth()
            .padding(vertical = 32.dp)
    ) {
        Text(
            text = "FAQ",
            modifier = Modifier.align(Alignment.CenterHorizontally),
            color = colorResource(id = R.color.ams),
            fontSize = 32.sp
        )
        Spacer(modifier = Modifier.height(16.dp))
            LazyColumn(modifier = Modifier.padding(horizontal = 24.dp)){
                item {
                    FaqItem(
                        "Why is the LED on the VU-AMS 5fs blinking rapidly?",
                        "A slow blink is a sign of standby, a faster blink means the " +
                                "VU-AMS is recording. A rapid blink means there is a problem. " +
                                "Please contact us at"
                    )
                    FaqItem(
                        "Why does the VU-AMS 5fs make a beeping sound?",
                        "See our status indicators page, you can find a list " +
                                "of all the status indicators and what they mean."
                    )
                    FaqItem(
                        "How do I interface with stimulus presentation software?",
                        "You can send markers from the stimulus computer to be recorded " +
                                "into the VU-AMS 5fs data file. "
                    )
                    FaqItem(
                        "Can I export my ECG data for use in the Kubios HRV analysis " +
                                "software package?",
                        "Many of the metrics available in Kubios are also available " +
                                "through VU-DAMS. To export your data in a Kubios-compatible" +
                                " format follow the procedure on the Compatible third parties page" +
                                " in the VU-DAMS user manual."
                    )
                    /*FaqItem(
                        "Put your question here",
                        "Put your answer here"
                    )*/
                }
            }
        }
    }

@Composable
fun FaqItem(title: String, content: String) {
    var expanded by remember { mutableStateOf(false) }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
    ) {
        Column(modifier = Modifier.clickable { expanded = !expanded }) {
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                    contentDescription = null,
                    tint = MaterialTheme.colors.onSurface
                )
            }
            if (expanded) {
                Text(
                    text = content,
                    modifier = Modifier.padding(16.dp),
                    fontSize = 14.sp,
                    lineHeight = 20.sp
                )
            }
        }
    }
}