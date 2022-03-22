package com.example.sportcelebrities.presentation.screens.home

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.sportcelebrities.ui.theme.colorTopAppBarBackground
import com.example.sportcelebrities.ui.theme.colorTopAppBarContent

@Composable
fun HomeTopBar(onSearchClicked: () -> Unit) {
    TopAppBar(
        title = {
            Text(
                text = "Главная",
                color = MaterialTheme.colors.colorTopAppBarContent
            )
        },
        backgroundColor = MaterialTheme.colors.colorTopAppBarBackground,
        actions = {
            IconButton(onClick = onSearchClicked) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Поиск"
                )
            }
        }
    )
}

@Preview
@Composable
fun HomeTopBarPreview() {
    HomeTopBar {

    }
}