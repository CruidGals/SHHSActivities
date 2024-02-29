package com.example.shhsactivities.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.shhsactivities.data.GoogleAuthApi

@Composable
fun MainScreenNavigation(
    modifier: Modifier,
    context: Context,
    googleAuthApi: GoogleAuthApi
) {
    //TODO
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "") {

    }
}