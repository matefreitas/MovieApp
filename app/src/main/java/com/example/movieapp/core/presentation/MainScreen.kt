package com.example.movieapp.core.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.movieapp.core.presentation.navigation.BottomnavigationBar
import com.example.movieapp.core.presentation.navigation.NavigationGraph

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    navHostController: NavHostController
) {
    Scaffold(
        bottomBar = {
            BottomnavigationBar(
                modifier = modifier,
                navController = navHostController
            )
        },
        content = { paddingValues ->
            Box(modifier = Modifier.padding(paddingValues)){
                NavigationGraph(
                    navHostController = navHostController
                )
            }
        }
    )
}