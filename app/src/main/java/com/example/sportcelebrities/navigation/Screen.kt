package com.example.sportcelebrities.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash_screen")
    object Welcome : Screen("welcome_screen")
    object Home : Screen("home_screen")
    object Details : Screen("details_screen/{celebrityId}") {
        fun passCelebrityId(celebrityId: Int): String {
            return "details_screen/$celebrityId"
        }
    }

    object Search : Screen("search_screen")
}
