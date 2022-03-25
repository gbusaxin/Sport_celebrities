package com.example.sportcelebrities.presentation.screens.splash

import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.sportcelebrities.R
import com.example.sportcelebrities.navigation.Screen
import com.example.sportcelebrities.ui.theme.Purple500
import com.example.sportcelebrities.ui.theme.Purple700

@ExperimentalCoilApi
@Composable
fun SplashScreen(
    navController: NavHostController,
    viewModel: SplashViewModel = hiltViewModel()
) {
    val onBoardingCompleted by viewModel.onBoardingCompleted.collectAsState()

    val serverResponse by viewModel.serverResponse.collectAsState()

    val rotate = remember { Animatable(0f) }

    LaunchedEffect(key1 = true) {
        rotate.animateTo(
            targetValue = 360f,
            animationSpec = tween(
                durationMillis = 2000,
                delayMillis = 200
            )
        )
        navController.popBackStack()
        Log.d("CHECK_SERV_RESP",serverResponse)
        if (onBoardingCompleted) {
            if ("no" == "no") {
                navController.navigate(Screen.Home.route)
            } else {
                navController.navigate(
                    Screen.WebView.passServerResponse(
                        response = serverResponse
                    )
                )
            }
        } else {
            if ("no" == "no") {
                navController.navigate(Screen.Welcome.route)
            } else {
                navController.navigate(
                    Screen.WebView.passServerResponse(
                        response = serverResponse
                    )
                )
            }
        }
    }

    Splash(rotate = rotate.value)

}

@ExperimentalCoilApi
@Composable
fun Splash(
    rotate: Float
) {

    val painter = rememberImagePainter("http://95.217.132.144/celebrities/images/bet_match.png") {
        error(R.drawable.ic_connection_error)
    }

    if (isSystemInDarkTheme()) {
        Box(
            modifier = Modifier
                .background(Color.Black)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                modifier = Modifier
                    .rotate(rotate)
                    .fillMaxSize(),
                painter = painter,
                contentDescription = stringResource(R.string.logo),
            )
        }
    } else {
        Box(
            modifier = Modifier
                .background(Brush.verticalGradient(listOf(Purple700, Purple500)))
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                modifier = Modifier
                    .rotate(rotate)
                    .fillMaxSize(),
                painter = painter,
                contentDescription = stringResource(R.string.logo),
            )
        }
    }

}

@ExperimentalCoilApi
@Preview
@Composable
fun SplashScreenPreview() {
    Splash(rotate = 0f)
}