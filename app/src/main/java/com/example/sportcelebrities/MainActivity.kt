package com.example.sportcelebrities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.sportcelebrities.navigation.SetupNavGraph
import com.example.sportcelebrities.ui.theme.SportCelebritiesTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SportCelebritiesTheme {

                navController = rememberNavController()
                SetupNavGraph(navController = navController)

            }
        }
    }
}