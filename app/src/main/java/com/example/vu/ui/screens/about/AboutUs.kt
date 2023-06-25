package com.example.vu.ui.screens.about

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.vu.R

@Composable
fun AboutUs(modifier: Modifier, navController: NavHostController) {

    Column(
        modifier
            .padding(start = 32.dp, end = 32.dp)
            .fillMaxSize()
    ) {
        Column(
            modifier
                .fillMaxSize()
                .padding(vertical = 32.dp)
        ) {

            Text(
                text = stringResource(id = R.string.about_us_title),
                modifier
                    .align(Alignment.CenterHorizontally),
                style = MaterialTheme.typography.h4,
                fontSize = 32.sp,
                color = colorResource(id = R.color.amsDark)
            )

            Column(
                modifier
                    .padding(top = 20.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.vu_university_ams),
                )
            }

            Spacer(
                modifier
                    .height(30.dp)
            )

            Text(
                text = stringResource(id = R.string.about_app_title),
                modifier
                    .align(Alignment.CenterHorizontally),
                style = MaterialTheme.typography.h4,
                fontSize = 32.sp,
                color = colorResource(id = R.color.amsDark)
            )

            Column(
                modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Column(
                    modifier
                        .padding(top = 20.dp)
                        .width(150.dp),
                ) {
                    Text(
                        text = stringResource(id = R.string.version_info),
                    )
                }
            }
        }
    }
}