package com.example.sportcelebrities.presentation.common

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.sportcelebrities.R
import com.example.sportcelebrities.domain.model.Celebrity
import com.example.sportcelebrities.navigation.Screen
import com.example.sportcelebrities.presentation.components.RatingWidget
import com.example.sportcelebrities.presentation.components.ShimmerEffect
import com.example.sportcelebrities.ui.theme.*

@ExperimentalCoilApi
@Composable
fun ListContent(
    celebrities: LazyPagingItems<Celebrity>,
    navController: NavHostController
) {
    val result = handlePagingResult(celebrities = celebrities)
    if (result) {
        LazyColumn(
            contentPadding = PaddingValues(all = SMALL_PADDING),
            verticalArrangement = Arrangement.spacedBy(SMALL_PADDING)
        ) {
            items(
                items = celebrities,
                key = { celebrity ->
                    celebrity.id
                }
            ) {
                it?.let {
                    Log.d("CHECK_RESULT_HANDLING", "$result inside ${it.name}")
                    CelebrityItem(celebrity = it, navController = navController)
                }
            }
        }
    }
    Log.d("CHECK_RESULT_HANDLING", "$result end")
}

@Composable
fun handlePagingResult(
    celebrities: LazyPagingItems<Celebrity>
): Boolean {
    celebrities.apply {
        val error = when {
            loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
            loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
            loadState.append is LoadState.Error -> loadState.append as LoadState.Error
            else -> null
        }
        Log.d("CHECK_ERROR_HANDLE", error.toString())
        return when {
            loadState.refresh is LoadState.Loading -> {
                ShimmerEffect()
                Log.d("CHECK_ERROR_refresh", error.toString())
                false
            }
            error != null -> {
                EmptyScreen(
                    error = error,
                    celebrities = celebrities
                )
                Log.d("CHECK_ERROR_null", error.toString())
                false
            }
            celebrities.itemCount < 1 -> {
                EmptyScreen()
                false
            }
            else -> {
                Log.d("CHECK_ERROR_else", error.toString())
                true
            }
        }
    }
}

@ExperimentalCoilApi
@Composable
fun CelebrityItem(
    celebrity: Celebrity,
    navController: NavHostController
) {

    val painter = rememberImagePainter(data = celebrity.image) {
        placeholder(R.drawable.ic_place_holder_for_image)
        error(R.drawable.ic_place_holder_for_image)
    }

    Box(
        modifier = Modifier
            .height(CELEBRITY_ITEM_HEIGHT)
            .clickable {
                navController.navigate(
                    Screen.Details.passCelebrityId(
                        celebrityId = celebrity.id
                    )
                )
            },
        contentAlignment = Alignment.BottomStart
    ) {
        Surface(shape = RoundedCornerShape(size = LARGE_PADDING)) {
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = painter,
                contentDescription = "image",
                contentScale = ContentScale.Crop
            )//image
        }//surface
        Surface(
            modifier = Modifier
                .fillMaxHeight(0.4f)
                .fillMaxWidth(),
            color = Color.Black.copy(alpha = ContentAlpha.medium),
            shape = RoundedCornerShape(
                bottomEnd = LARGE_PADDING,
                bottomStart = LARGE_PADDING
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(all = MEDIUM_PADDING)
            ) {
                Text(
                    text = celebrity.name,
                    color = MaterialTheme.colors.colorTopAppBarContent,
                    fontSize = MaterialTheme.typography.h5.fontSize,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = celebrity.about,
                    color = Color.White.copy(alpha = ContentAlpha.medium),
                    fontSize = MaterialTheme.typography.subtitle1.fontSize,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
                Row(
                    modifier = Modifier.padding(top = SMALL_PADDING),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RatingWidget(
                        modifier = Modifier.padding(end = SMALL_PADDING),
                        rating = celebrity.rating
                    )
                    Text(
                        text = "(${celebrity.rating})",
                        textAlign = TextAlign.Center,
                        color = Color.White.copy(alpha = ContentAlpha.medium)
                    )
                }//row
            }//column
        }//surface
    }//box
}

@ExperimentalCoilApi
@Preview
@Composable
fun CelebrityItemPreview() {
    CelebrityItem(
        celebrity = Celebrity(
            id = 1,
            name = "greg",
            image = "http://95.217.132.144/celebrities/images/Cristiano_Ronaldo_2018.jpg",
            about = "fosnefowneofnwoenfowenfownoefnwoef wf hweifhow eifhwoef ow efhweof owifhweof whfoi wehfowefhow e f",
            rating = 4.4,
            power = 98,
            month = "january",
            day = "20",
            teams = listOf(
                "feniks",
                "star",
                "soczniowec"
            ),
            prizes = listOf(
                "1",
                "2",
                "3"
            )
        ),
        navController = rememberNavController()
    )
}