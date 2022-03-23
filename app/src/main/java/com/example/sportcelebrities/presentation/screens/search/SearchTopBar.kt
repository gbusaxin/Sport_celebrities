package com.example.sportcelebrities.presentation.screens.search

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import com.example.sportcelebrities.ui.theme.TOP_APP_BAR_HEIGHT
import com.example.sportcelebrities.ui.theme.colorTopAppBarBackground
import com.example.sportcelebrities.ui.theme.colorTopAppBarContent

@Composable
fun SearchTopBar(
    text: String,
    onTextChanged: (String) -> Unit,
    onSearchClicked: (String) -> Unit,
    onCloseClicked: () -> Unit
) {
    SearchWidget(
        text =text,
        onTextChanged =onTextChanged,
        onSearchClicked =onSearchClicked,
        onCloseClicked = onCloseClicked
    )
}

@Composable
fun SearchWidget(
    text: String,
    onTextChanged: (String) -> Unit,
    onSearchClicked: (String) -> Unit,
    onCloseClicked: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(TOP_APP_BAR_HEIGHT),
        elevation = AppBarDefaults.TopAppBarElevation,
        color = MaterialTheme.colors.colorTopAppBarBackground
    ) {
        TextField(
            modifier = Modifier
                .fillMaxWidth(),
            placeholder = {
                          Text(
                              modifier = Modifier.alpha(alpha = ContentAlpha.medium),
                              text = "Поиск...",
                              color = Color.White
                          )
            },
            value = text,
            onValueChange = { onTextChanged(it) },
            textStyle = TextStyle(
                color = MaterialTheme.colors.colorTopAppBarContent
            ),
            singleLine = true,
            leadingIcon = {
                IconButton(
                    modifier = Modifier.alpha(alpha = ContentAlpha.medium),
                    onClick = {}
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Поиск",
                        tint = MaterialTheme.colors.colorTopAppBarContent
                    )
                }//IconButtonSearch
            },
            trailingIcon = {
                IconButton(
                    onClick = {
                        if (text.isNotEmpty()){
                            onTextChanged("")
                        }else{
                            onCloseClicked()
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Отменить",
                        tint = MaterialTheme.colors.colorTopAppBarContent
                    )
                }//IconButtonTrailing
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearchClicked(text)
                }
            ),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                cursorColor = MaterialTheme.colors.colorTopAppBarContent
            )
        )//TextField
    }
}

@Preview
@Composable
fun SearchWidgetPreview() {
    SearchWidget(
        text = "",
        onTextChanged = {},
        onSearchClicked = {},
        onCloseClicked = {}
    )
}