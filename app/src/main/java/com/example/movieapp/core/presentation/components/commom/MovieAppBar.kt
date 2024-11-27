package com.example.movieapp.core.presentation.components.commom

import androidx.annotation.StringRes
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.example.movieapp.ui.theme.black
import com.example.movieapp.ui.theme.white

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieAppBar(
    modifier: Modifier = Modifier,
    @StringRes title: Int,
    textColor: Color = white,
    backgorundColor: Color = black
) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(title)
            )
        },
        modifier = modifier
    )
}