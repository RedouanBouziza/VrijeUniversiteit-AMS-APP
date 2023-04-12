package com.example.vu.ui.screens.menu

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.vu.R


@Composable
fun MenuHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 64.dp),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.ams),
            contentDescription = "Logo",
        )
    }
}

@Composable
fun MenuBody(
    modifier: Modifier = Modifier,
    itemTextStyle: TextStyle = TextStyle(fontSize = 18.sp),
    onItemClick: (MenuItem) -> Unit
) {
    val menuItems = listOf<MenuItem>(
        MenuItem(
            id = "home",
            title = "Home",
            contentDescription = "Homepage",
            icon = Icons.Default.Home
        ),
        MenuItem(
            id = "chart",
            title = "Chart",
            contentDescription = "Chart-screen",
            icon = Icons.Default.Build
        ),
        MenuItem(
            id = "faq",
            title = "FAQ",
            contentDescription = "Faq-screen",
            icon = Icons.Default.Info
        ),
        MenuItem(
            id = "system",
            title = "System",
            contentDescription = "Device settings",
            icon = Icons.Default.Settings
        )
    )

    LazyColumn(modifier) {
        items(menuItems) { item ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onItemClick(item)
                    }
                    .padding(16.dp)
            ) {
                Icon(
                    imageVector = item.icon,
                    contentDescription = item.contentDescription
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = item.title,
                    style = itemTextStyle,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}
