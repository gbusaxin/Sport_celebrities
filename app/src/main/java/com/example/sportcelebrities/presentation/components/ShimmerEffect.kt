package com.example.sportcelebrities.presentation.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import com.example.sportcelebrities.ui.theme.*

@Composable
fun ShimmerEffect() {
    LazyColumn(
        contentPadding = PaddingValues(all = SMALL_PADDING),
        verticalArrangement = Arrangement.spacedBy(SMALL_PADDING)
    ){
        items(count = 2){
            AnimatedShimmerEffect()
        }
    }
}

@Composable
fun ShimmerItem(alpha:Float) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(CELEBRITY_ITEM_HEIGHT),
        color = if (isSystemInDarkTheme()) Color.Black else ShimmerLightGrey,
        shape = RoundedCornerShape(size = LARGE_PADDING)
    ) {
        Column(
            modifier = Modifier
                .padding(all = MEDIUM_PADDING),
            verticalArrangement = Arrangement.Bottom
        ) {
            Surface(
                modifier = Modifier
                    .alpha(alpha = alpha)
                    .fillMaxWidth(0.5f)
                    .height(NAME_PLACEHOLDER_HEIGHT),
                color = if (isSystemInDarkTheme()) ShimmerDarkGrey else ShimmerMediumGrey,
                shape = RoundedCornerShape(size = SMALL_PADDING)
            ) {}//surface2
            Spacer(
                modifier = Modifier.padding(all = SMALL_PADDING)
            )
            repeat(3) {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .alpha(alpha = alpha)
                        .height(ABOUT_PLACEHOLDER_HEIGHT),
                    color = if (isSystemInDarkTheme()) ShimmerDarkGrey else ShimmerMediumGrey,
                    shape = RoundedCornerShape(size = SMALL_PADDING)
                ) {}
                Spacer(
                    modifier = Modifier.padding(all = EXTRA_SMALL_PADDING)
                )
            }//repeat
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                repeat(5) {
                    Surface(
                        modifier = Modifier
                            .alpha(alpha = alpha)
                            .size(STAR_PLACEHOLDER_HEIGHT),
                        color = if (isSystemInDarkTheme()) ShimmerDarkGrey else ShimmerMediumGrey,
                        shape = RoundedCornerShape(size = SMALL_PADDING)
                    ) {}
                    Spacer(
                        modifier = Modifier.padding(all = SMALL_PADDING)
                    )
                }//repeat
            }//row
        }//column
    }//surface 1
}

@Composable
fun AnimatedShimmerEffect() {
    val transition = rememberInfiniteTransition()
    val alphaAnim = transition.animateFloat(
        initialValue =1f,
        targetValue =0f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 500,
                easing = FastOutLinearInEasing
            ),
            repeatMode = RepeatMode.Reverse
        )
    )
    ShimmerItem(alpha = alphaAnim.value)
}