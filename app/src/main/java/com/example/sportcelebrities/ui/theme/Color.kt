package com.example.sportcelebrities.ui.theme

import androidx.compose.material.Colors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Purple200 = Color(0xFFBB86FC)
val Purple500 = Color(0xFF6200EE)
val Purple700 = Color(0xFF3700B3)
val Teal200 = Color(0xFF03DAC5)
val DarkOrange = Color(0xffff7700)
val LightOrange = Color(0xffffa04d)
val DarkGrey = Color(0xFF696969)
val LightGrey = Color(0xFFD3D3D3)

val Colors.welcomeScreenBackgroundColor
    @Composable
    get() = if (isLight) Purple200 else Color.Black

val Colors.colorTitle
    @Composable
    get() = if (isLight) DarkGrey else LightGrey

val Colors.colorDescription
    @Composable
    get() = if (isLight) DarkGrey.copy(alpha = 0.5f) else LightGrey.copy(alpha = 0.5f)

val Colors.colorActiveIndicator
    @Composable
    get() = if (isLight) DarkOrange else LightOrange

val Colors.colorInactiveIndicator
    @Composable
    get() = if (isLight) DarkGrey else LightGrey

val Colors.colorBackgroundButton
    @Composable
    get() = if (isLight) LightOrange else DarkOrange