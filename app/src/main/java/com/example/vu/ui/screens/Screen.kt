package com.example.vu.ui.screens

sealed class Screen(
    val route: String
) {
    object HomePage: Screen("home_page")
    object ChartPage: Screen("chart_page")
}

