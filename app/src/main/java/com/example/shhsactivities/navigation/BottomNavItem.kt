package com.example.shhsactivities.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shhsactivities.util.Routes

@Composable
fun BottomNavItem(
    icon: ImageVector,
    name: String,
    selected: Boolean,
    selectedContentColor: Color = Color.Black,
    unselectedContentColor: Color = Color.White,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier.padding(3.dp).clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            icon,
            icon.name,
            tint = if(selected) selectedContentColor else unselectedContentColor,
            modifier = Modifier.padding(top = 8.dp, start = 12.dp, end = 12.dp).scale(1.5f)
        )

        Text(
            text = name,
        )
    }
}

/*
 * Purpose of this class is mainly for the validSelectedRoutes variable
 * It allows saving screen state of other screens when on a certain bottom nav item
 */
sealed class BottomNavObject (
    val route: String,
    val validSelectedRoutes: Set<String>
) {
    data object Home : BottomNavObject(
        route = Routes.HOME.route,
        validSelectedRoutes = setOf(
            Routes.HOME.route,
            "${Routes.CLUB.route}?clubId={clubId}",
            "${Routes.SEARCH.route}?clubIds={clubIds}"
        )
    )

    data object Search : BottomNavObject(
        route = Routes.SEARCH.route,
        validSelectedRoutes = setOf(
            "${Routes.SEARCH.route}?clubIds={clubIds}",
            "${Routes.CLUB.route}?clubId={clubId}"
        )
    )

    data object Menu : BottomNavObject(
        route = Routes.MENU.route,
        validSelectedRoutes = setOf(
            Routes.MENU.route,
            Routes.NOTIFICATION.route,
            Routes.DISPLAY.route,
            Routes.PROFILE.route
        )
    )

    companion object {
        val bottomNavObjectList = listOf(
            Home, Search, Menu
        )
    }

}

/*
 * Only accepts Routes on the bottom nav bar
 */
fun iconVectorFromBottomNavObj(obj: BottomNavObject): ImageVector {
    return when(obj) {
        is BottomNavObject.Home -> Icons.Default.Home
        is BottomNavObject.Search -> Icons.Default.Search
        is BottomNavObject.Menu -> Icons.Default.Menu
        else -> {Icons.Default.Info} // Could be any image
    }
}