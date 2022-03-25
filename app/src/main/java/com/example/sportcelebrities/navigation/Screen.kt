package com.example.sportcelebrities.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash_screen")

    object Welcome : Screen("welcome_screen")

    object Home : Screen("home_screen")

    object WebView : Screen("web_view_screen/{servers_response}"){
        fun passServerResponse(response: String): String {
            return "web_view_screen/$response"
        }
    }

    object Details : Screen("details_screen/{celebrityId}") {
        fun passCelebrityId(celebrityId: Int): String {
            return "details_screen/$celebrityId"
        }
    }

    object Search : Screen("search_screen")
}
