package com.example.shhsactivities.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.shhsactivities.ui.screens.components.ErrorScreen
import com.example.shhsactivities.ui.screens.components.LoadingScreen
import com.example.shhsactivities.ui.states.UserRetrievalState
import com.example.shhsactivities.ui.theme.Typography
import com.example.shhsactivities.ui.viewmodels.MenuViewModel
import com.example.shhsactivities.util.Routes

@Composable
fun MenuScreen(
    onNavigate: (Routes) -> Unit,
    onLogOut: () -> Unit,
    viewModel: MenuViewModel = hiltViewModel()
) {
    val user = viewModel.user.collectAsState().value

    when(user) {
        is UserRetrievalState.Success -> {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .then(Modifier.statusBarsPadding())
                        .align(Alignment.TopCenter)
                ) {
                    Box(
                        modifier = Modifier.fillMaxWidth().background(Color.DarkGray).padding(8.dp)
                    ) {
                        Text(
                            text = "Menu",
                            style = Typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            modifier = Modifier.padding(
                                start = 6.dp,
                                top = 32.dp,
                                bottom = 6.dp
                            )
                        )
                    }

                    MenuButton(title = "Account", onClick = { onNavigate(Routes.PROFILE) })
                    MenuButton(title = "Display", onClick = { onNavigate(Routes.DISPLAY) })
                    MenuButton(title = "Notifications", onClick = { onNavigate(Routes.NOTIFICATION) })
                }

                Button(
                    onClick = {
                        viewModel.logOut()
                        onLogOut() },
                    modifier = Modifier.align(Alignment.BottomEnd)
                ) {
                    Text(text = "Log Out")
                }
            }
        }
        UserRetrievalState.Error -> ErrorScreen(modifier = Modifier.fillMaxSize(), error = "loading user")
        UserRetrievalState.Loading -> LoadingScreen(modifier = Modifier.fillMaxSize())
    }
}

@Composable
fun MenuButton(
    title: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = Typography.headlineSmall,
            fontWeight = FontWeight.Medium,
            modifier = Modifier
                .weight(1f)
                .padding(4.dp)
        )

        Icon(
            Icons.Default.KeyboardArrowRight,
            Icons.Default.KeyboardArrowRight.name,
            tint = Color.LightGray,
            modifier = Modifier.padding(horizontal = 5.dp)
        )
    }
}