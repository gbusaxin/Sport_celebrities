package com.example.sportcelebrities.presentation.screens.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavHostController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.sportcelebrities.R
import com.example.sportcelebrities.domain.model.Celebrity
import com.example.sportcelebrities.presentation.components.InfoBox
import com.example.sportcelebrities.presentation.components.OrderedList
import com.example.sportcelebrities.ui.theme.*
import com.example.sportcelebrities.utils.Constants.MAX_LINES_FOR_ABOUT_TEXT

@ExperimentalCoilApi
@ExperimentalMaterialApi
@Composable
fun DetailsContent(
    navController: NavHostController,
    celebrity: Celebrity?
) {
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberBottomSheetState(initialValue = BottomSheetValue.Expanded)
    )

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetPeekHeight = MIN_SHEET_HEIGHT,
        sheetContent = {
            celebrity?.let { BottomSheetContent(selectedCelebrity = it) }
        },
        content = {
            celebrity?.image?.let { it1 ->
                BackgroundContent(
                    backgroundImage = it1,
                    onCloseClicked = {navController.popBackStack()}
                )
            }
        }
    )
}

@ExperimentalCoilApi
@Composable
fun BackgroundContent(
    backgroundImage: String,
    imageFraction: Float = 1f,
    backgroundColor: Color = MaterialTheme.colors.surface,
    onCloseClicked: () -> Unit
) {
    val painter = rememberImagePainter(backgroundImage){
        error(R.drawable.ic_place_holder_for_image)
    }

    Box(modifier = Modifier
        .fillMaxSize()
        .background(backgroundColor)
    ){
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(fraction = imageFraction)
                .align(Alignment.TopStart),
            painter = painter,
            contentDescription = "placeholder",
            contentScale = ContentScale.Crop
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            IconButton(
                modifier = Modifier.padding(all = SMALL_PADDING),
                onClick = onCloseClicked
            ) {
                Icon(
                    modifier = Modifier.size(CLOSE_ICON_SIZE),
                    imageVector = Icons.Default.Close,
                    contentDescription = "закрыть",
                    tint = Color.White
                )
            }
        }
    }
}

@Composable
fun BottomSheetContent(
    selectedCelebrity: Celebrity,
    infoBoxIconColor: Color = MaterialTheme.colors.primary,
    sheetBackgroundColor: Color = MaterialTheme.colors.surface,
    contentColor: Color = MaterialTheme.colors.colorTitle
) {
    Column(
        modifier = Modifier
            .background(sheetBackgroundColor)
            .padding(all = LARGE_PADDING)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = LARGE_PADDING),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = selectedCelebrity.name,
                color = contentColor,
                fontSize = MaterialTheme.typography.h4.fontSize,
                fontWeight = FontWeight.Bold
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = MEDIUM_PADDING),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            InfoBox(
                icon = painterResource(id = R.drawable.ic_lighting_image),
                iconColor = infoBoxIconColor,
                bigText = "${selectedCelebrity.power}",
                smallText = "Сила",
                textColor = contentColor
            )
            InfoBox(
                icon = painterResource(id = R.drawable.ic_calendar_image),
                iconColor = infoBoxIconColor,
                bigText = selectedCelebrity.month,
                smallText = "Месяц",
                textColor = contentColor
            )
            InfoBox(
                icon = painterResource(id = R.drawable.ic_birthday_image),
                iconColor = infoBoxIconColor,
                bigText = selectedCelebrity.day,
                smallText = "День",
                textColor = contentColor
            )
        }//row
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Главное:",
            color = contentColor,
            fontSize = MaterialTheme.typography.subtitle1.fontSize,
            fontWeight = FontWeight.Bold

        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .alpha(ContentAlpha.medium)
                .padding(bottom = MEDIUM_PADDING),
            text = selectedCelebrity.about,
            color = contentColor,
            fontSize = MaterialTheme.typography.body1.fontSize,
            maxLines = MAX_LINES_FOR_ABOUT_TEXT
        )
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            OrderedList(
                title = "Награды:",
                list = selectedCelebrity.prizes,
                textColor = contentColor
            )
            OrderedList(
                title = "Команды:",
                list = selectedCelebrity.teams,
                textColor = contentColor
            )
        }
    }//column
}