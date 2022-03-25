package com.example.sportcelebrities.domain.model

import androidx.annotation.DrawableRes
import androidx.annotation.Keep
import com.example.sportcelebrities.R

sealed class OnBoardingPage(
    @DrawableRes
    val image: Int,
    val title: String,
    val description: String
) {

    object StartPage : OnBoardingPage(
        image = R.drawable.welcome,
        title = "Добро пожаловать!",
        description = "В этом приложении вы можете ознакомиться со звездами спорта."
    )

    object LastPage : OnBoardingPage(
        image = R.drawable.ready,
        title = "Вы готовы?",
        description = "Для того чтобы продолжить нажмите кнопку ниже."
    )

}
