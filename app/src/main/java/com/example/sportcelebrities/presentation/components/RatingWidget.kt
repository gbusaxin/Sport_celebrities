package com.example.sportcelebrities.presentation.components

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.vector.PathParser
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.sportcelebrities.R
import com.example.sportcelebrities.ui.theme.EXTRA_SMALL_PADDING
import com.example.sportcelebrities.ui.theme.LightOrange
import com.example.sportcelebrities.utils.Constants.EMPTY_STARS_KEY
import com.example.sportcelebrities.utils.Constants.FILLED_STARS_KEY
import com.example.sportcelebrities.utils.Constants.HALF_FILLED_STARS_KEY

@Composable
fun RatingWidget(
    modifier: Modifier,
    rating: Double,
    scaleFactor: Float = 3f,
    spaceBetween: Dp = EXTRA_SMALL_PADDING
) {

    val result = calculateStars(rating = rating)

    val starStringPath = stringResource(id = R.string.start_path)
    val starPath = remember {
        PathParser().parsePathString(pathData = starStringPath).toPath()
    }
    val starPathBounce = remember {
        starPath.getBounds()
    }

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(space = spaceBetween)
    ) {
        result[FILLED_STARS_KEY]?.let {
            repeat(it) {
                FilledStar(
                    starPath = starPath,
                    starBound = starPathBounce,
                    scaleFactor = scaleFactor
                )
            }
        }
        result[HALF_FILLED_STARS_KEY]?.let {
            repeat(it) {
                HalfFilledStar(
                    starPath = starPath,
                    starBound = starPathBounce,
                    scaleFactor = scaleFactor
                )
            }
        }
        result[EMPTY_STARS_KEY]?.let {
            repeat(it) {
                EmptyStar(
                    starPath = starPath,
                    starBound = starPathBounce,
                    scaleFactor = scaleFactor
                )
            }
        }
    }//row

}

@Composable
fun FilledStar(
    starPath: Path,
    starBound: Rect,
    scaleFactor: Float
) {
    Canvas(modifier = Modifier.size(24.dp)) {
        val canvasSize = size
        scale(scale = scaleFactor) {
            val pathWidth = starBound.width
            val pathHeight = starBound.height
            val left = (canvasSize.width / 2f) - (pathWidth / 1.5f)
            val top = (canvasSize.height / 2f) - (pathHeight / 1.5f)
            translate(left = left, top = top) {
                drawPath(
                    path = starPath,
                    color = LightOrange
                )
            }//translate
        }//scale
    }//canvas
}

@Composable
fun HalfFilledStar(
    starPath: Path,
    starBound: Rect,
    scaleFactor: Float
) {
    Canvas(modifier = Modifier.size(24.dp)) {
        val canvasSize = size
        scale(scale = scaleFactor) {
            val pathWidth = starBound.width
            val pathHeight = starBound.height
            val left = (canvasSize.width / 2f) - (pathWidth / 1.5f)
            val top = (canvasSize.height / 2f) - (pathHeight / 1.5f)
            translate(left = left, top = top) {
                drawPath(
                    path = starPath,
                    color = Color.LightGray.copy(alpha = 0.5f)
                )
                clipPath(path = starPath) {
                    drawRect(
                        color = LightOrange,
                        size = Size(
                            width = starBound.maxDimension / 1.5f,
                            height = starBound.maxDimension * scaleFactor
                        )//size
                    )//drawRect
                }//clipPath
            }
        }//scale
    }//Canvas
}


@Composable
fun EmptyStar(
    starPath: Path,
    starBound: Rect,
    scaleFactor: Float
) {
    Canvas(modifier = Modifier.size(24.dp)) {
        val canvasSize = size
        scale(scale = scaleFactor) {
            val pathWidth = starBound.width
            val pathHeight = starBound.height
            val left = (canvasSize.width / 2f) - (pathWidth / 1.5f)
            val top = (canvasSize.height / 2f) - (pathHeight / 1.5f)
            translate(left = left, top = top) {
                drawPath(
                    path = starPath,
                    color = Color.LightGray.copy(alpha = 0.5f)
                )
            }//translate
        }//scale
    }//canvas
}

@Composable
fun calculateStars(rating: Double): Map<String, Int> {
    val maxStars by remember { mutableStateOf(5) }
    var filledStars by remember { mutableStateOf(0) }
    var emptyStars by remember { mutableStateOf(0) }
    var halfStars by remember { mutableStateOf(0) }

    LaunchedEffect(key1 = rating) {
        val (firstNumber, lastNumber) = rating.toString()
            .split(".")
            .map { it.toInt() }
        if (firstNumber in 0..5 && lastNumber in 0..9) {
            filledStars = firstNumber
            if (lastNumber in 1..5) {
                halfStars++
            }
            if (lastNumber in 6..9) {
                filledStars++
            }
            if (firstNumber == 5 && lastNumber > 0) {
                emptyStars = 5
                filledStars = 0
                halfStars = 0
            }
        } else {
            Log.d("CHECK_RATING_CALC", "Something went wrong with rating calculation")
        }
    }
    emptyStars = maxStars - (filledStars + halfStars)
    return mapOf(
        FILLED_STARS_KEY to filledStars,
        EMPTY_STARS_KEY to emptyStars,
        HALF_FILLED_STARS_KEY to halfStars
    )
}

@Preview(showBackground = true)
@Composable
fun FilledStarPreview() {
    val starStringPath = stringResource(id = R.string.start_path)
    val starPath = remember {
        PathParser().parsePathString(pathData = starStringPath).toPath()
    }
    val starPathBounce = remember {
        starPath.getBounds()
    }
    FilledStar(starBound = starPathBounce, starPath = starPath, scaleFactor = 3f)
}

@Preview(showBackground = true)
@Composable
fun HalfFilledPreview() {
    val starStringPath = stringResource(id = R.string.start_path)
    val starPath = remember {
        PathParser().parsePathString(pathData = starStringPath).toPath()
    }
    val starPathBounce = remember {
        starPath.getBounds()
    }
    HalfFilledStar(starBound = starPathBounce, starPath = starPath, scaleFactor = 3f)
}

@Preview(showBackground = true)
@Composable
fun EmptyStarPreview() {
    val starStringPath = stringResource(id = R.string.start_path)
    val starPath = remember {
        PathParser().parsePathString(pathData = starStringPath).toPath()
    }
    val starPathBounce = remember {
        starPath.getBounds()
    }
    EmptyStar(starBound = starPathBounce, starPath = starPath, scaleFactor = 3f)
}