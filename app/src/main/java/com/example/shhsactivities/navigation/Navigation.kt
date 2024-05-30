package com.example.shhsactivities.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.shhsactivities.ui.screens.CatalogScreen
import com.example.shhsactivities.ui.screens.ClubScreen
import com.example.shhsactivities.ui.screens.HomeScreen
import com.example.shhsactivities.ui.screens.MenuScreen
import com.example.shhsactivities.ui.screens.SignInScreen
import com.example.shhsactivities.util.Routes
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavSetup() {
    val navController = rememberNavController()
    val showBottomNavBar = rememberSaveable { mutableStateOf(true) }
    
    if(getCurrentRoute(navController = navController) == Routes.SIGN_IN.route) {
        showBottomNavBar.value = false
    }

    Scaffold(
        bottomBar = {
            AnimatedVisibility(
                visible = showBottomNavBar.value,
                enter = expandVertically(
                    animationSpec = tween(durationMillis = 500),
                    expandFrom = Alignment.Bottom
                ),
                exit = shrinkVertically(
                    animationSpec = tween(durationMillis = 500),
                    shrinkTowards = Alignment.Bottom
                ),
                label = "Bottom Bar"
            ) {
                BottomNavigationBar(navController = navController)
            }
        }
    ) { innerPadding ->
        MainScreenNavigation(
            modifier = Modifier.padding(innerPadding),
            showBottomNavBar = showBottomNavBar,
            navController = navController
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainScreenNavigation(
    showBottomNavBar: MutableState<Boolean>,
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String = Routes.HOME.route,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
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
                navController.navigate(Routes.SIGN_IN.route)
            })
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    NavigationBar (
        modifier = Modifier.fillMaxWidth(),
        containerColor = Color.White,
        contentColor = Color.Gray
    ) {
        Row(
            modifier = Modifier.fillMaxSize().background(Color.LightGray),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            val currentRoute = getCurrentRoute(navController)
            BottomNavObject.bottomNavObjectList.forEach { obj ->
                BottomNavItem(
                    icon = iconVectorFromBottomNavObj(obj),
                    name = obj.route.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() },
                    selected = obj.validSelectedRoutes.contains(currentRoute),
                    onClick = {
                        navController.navigate(obj.route)
                    }
                )
            }
        }
    }
}

@Composable
private fun getCurrentRoute(navController: NavController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}