package com.example.shhsactivities.navigation

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.shhsactivities.data.GoogleAuthApi
import com.example.shhsactivities.ui.screens.CatalogScreen
import com.example.shhsactivities.ui.screens.ClubScreen
import com.example.shhsactivities.ui.screens.HomeScreen
import com.example.shhsactivities.ui.screens.MenuScreen
import com.example.shhsactivities.ui.screens.SignInScreen
import com.example.shhsactivities.ui.screens.TestScreen
import com.example.shhsactivities.util.Routes
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainScreenNavigation(
    context: Context,
    googleAuthApi: GoogleAuthApi,
    scope: LifecycleCoroutineScope,
    modifier: Modifier = Modifier,
    startDestination: String = Routes.HOME.route
) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = startDestination) {
        composable(Routes.HOME.route) {
            HomeScreen(onClickClub = {})
        }

        composable(
            route = "${Routes.SEARCH.route}?clubIds={clubIds}",
            arguments = listOf(
                navArgument("clubIds") {
                    type = NavType.StringType
                    defaultValue = ""
                }
            )
        ) {
            CatalogScreen(onClickClub = {
                if(it != "-1") {
                    navController.navigate("${Routes.CLUB.route}?clubId=$it")
                }
            })
        }

        composable(
            route = "${Routes.CLUB.route}?clubId={clubId}",
            arguments = listOf(
                navArgument("clubId") {
                    type = NavType.StringType
                    defaultValue = "-1"
                }
            )
        ) {
            ClubScreen(onClickBack = { navController.popBackStack() })
        }

        composable(Routes.SIGN_IN.route) {
            SignInScreen(onNavigate = {
                navController.navigate(Routes.HOME.route)
            })
        }

        composable(
            Routes.MENU.route
        ) {
            MenuScreen(onNavigate = {
                navController.navigate(it.route)
            }, onLogOut = {
                scope.launch {
                    googleAuthApi.signOut()
                }

                navController.navigate(Routes.SIGN_IN.route)
            })
        }
    }
}