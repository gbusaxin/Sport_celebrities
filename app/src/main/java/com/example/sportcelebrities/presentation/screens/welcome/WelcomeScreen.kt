package com.example.sportcelebrities.presentation.screens.welcome

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.sportcelebrities.R
import com.example.sportcelebrities.domain.model.OnBoardingPage
import com.example.sportcelebrities.navigation.Screen
import com.example.sportcelebrities.ui.theme.*
import com.google.accompanist.pager.*

@ExperimentalPagerApi
@Composable
fun WelcomeScreen(
    navController: NavHostController,
    viewModel: WelcomeViewModel = hiltViewModel()
) {

    val pages = listOf(
        OnBoardingPage.StartPage,
        OnBoardingPage.LastPage
    )

    val pagerState = rememberPagerState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.welcomeScreenBackgroundColor)
    ) {
        HorizontalPager(
            modifier = Modifier.weight(10f),
            state = pagerState,
            count = pages.size,
            verticalAlignment = Alignment.Top
        ) {
            PagerScreen(onBoardingPage = pages[it])
        }
        HorizontalPagerIndicator(
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterHorizontally),
            pagerState = pagerState,
            inactiveColor = MaterialTheme.colors.colorInactiveIndicator,
            activeColor = MaterialTheme.colors.colorActiveIndicator,
            indicatorWidth = PAGGING_INDICATOR_WIDTH,
            spacing = PAGGING_INDICATOR_SPACING
        )
        ReadyButton(
            pagerState = pagerState,
            modifier = Modifier.weight(1f)
        ) {
            navController.popBackStack()
            navController.navigate(Screen.Home.route)
            viewModel.saveOnBoardingState(completed = true)
        }
    }

}

@Composable
fun PagerScreen(onBoardingPage: OnBoardingPage) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Image(
            modifier = Modifier
                .fillMaxHeight(0.7f)
                .padding(horizontal = SMALL_PADDING),
            painter = painterResource(id = onBoardingPage.image),
            contentDescription = stringResource(id = R.string.logo)
        )
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = onBoardingPage.title,
            color = MaterialTheme.colors.colorTitle,
            fontSize = MaterialTheme.typography.h4.fontSize,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = SMALL_PADDING),
            text = onBoardingPage.description,
            color = MaterialTheme.colors.colorDescription,
            fontSize = MaterialTheme.typography.subtitle1.fontSize,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center
        )
    }
}

@ExperimentalPagerApi
@Composable
fun ReadyButton(
    pagerState: PagerState,
    modifier: Modifier,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier.padding(horizontal = EXTRA_LARGE_PADDING),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.Center
    ) {
        AnimatedVisibility(
            modifier = Modifier.fillMaxWidth(),
            visible = pagerState.currentPage == 1
        ) {
            Button(
                onClick = onClick,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.colorBackgroundButton,
                    contentColor = Color.White
                )
            ) {
                Text(text = "Готов")
            }
        }
    }
}

@Composable
@Preview
fun StartScreenOnBoardPreview() {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        PagerScreen(onBoardingPage = OnBoardingPage.StartPage)
    }
}