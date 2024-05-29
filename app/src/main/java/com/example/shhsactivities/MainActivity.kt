package com.example.shhsactivities

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.shhsactivities.data.GoogleAuthApi
import com.example.shhsactivities.data.repositories.UserPreferencesRepository
import com.example.shhsactivities.navigation.MainScreenNavigation
import com.example.shhsactivities.navigation.NavSetup
import com.example.shhsactivities.ui.screens.CatalogScreen
import com.example.shhsactivities.ui.screens.HomeScreen
import com.example.shhsactivities.ui.screens.MenuScreen
import com.example.shhsactivities.ui.screens.SignInScreen
import com.example.shhsactivities.ui.screens.TestScreen
import com.example.shhsactivities.ui.theme.SHHSActivitiesTheme
import com.example.shhsactivities.util.Routes
import com.example.shhsactivities.util.userPreferencesStore
import com.google.android.gms.auth.api.identity.Identity
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val googleAuthApi by lazy {
        GoogleAuthApi(
            applicationContext,
            Identity.getSignInClient(applicationContext),
            Firebase.auth
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            SHHSActivitiesTheme {
                Surface (
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavSetup()
                }
            }
        }
    }
}

private val testClubIds = listOf(
    "2WEmBF7dGVPshDa2Lgr9",
    "Fe0OgLy8NS4qZxzjRfUp",
    "HdL6Sp6vEMFPQ3RfGPC6",
    "IBkyb0Tn1ch02lFYVbO6",
    "ch1bGuAZPfxXO8ap3hTB"
)