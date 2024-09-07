package com.jlrf.mobile.employeepedia.presentation.compose.navigation

sealed class CustomAppNavigation(val route: String) {
    object HomeScreen : CustomAppNavigation(route = "home_screen")
    object MovieDetailsScreen : CustomAppNavigation(route = "movie_details_screen")

    companion object {
        const val NAV_GRAPH_NAME = "main_activity"
    }
}