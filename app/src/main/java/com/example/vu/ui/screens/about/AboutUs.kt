package com.example.vu.ui.screens.faq

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.vu.R

@Composable
fun AboutUs(navController: NavHostController) {

    Column(
        modifier = Modifier
            .padding(18.dp)
            .fillMaxSize()
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Text(
                text = "ABOUT",
                style = MaterialTheme.typography.h4,
                fontWeight = FontWeight.Bold,
                color = colorResource(id = R.color.amsDark)
            )
            Column(
                modifier = Modifier
                    .padding(top = 20.dp)
            ) {
                Text(
                    text = "The VU University Ambulatory Monitoring System, created by the department " +
                            "of Biological Psychology and the Technical Department of the Faculty of " +
                            "Psychology and Education, enables monitoring of autonomic and cardiovascular " +
                            "activity in research settings, including ambulatory monitoring in naturalistic " +
                            "environments. The device and its software program meet high technical standards, " +
                            "with support from the ITM of the Faculty of Psychology and Education"
                )
            }
        }
    }

}