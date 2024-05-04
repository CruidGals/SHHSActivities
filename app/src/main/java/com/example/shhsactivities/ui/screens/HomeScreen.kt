package com.example.shhsactivities.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.shhsactivities.data.models.Announcement
import com.example.shhsactivities.data.models.Club
import com.example.shhsactivities.ui.components.general.DropDownMenu
import com.example.shhsactivities.ui.screens.components.AnnouncementItem
import com.example.shhsactivities.ui.screens.components.ClubItem
import com.example.shhsactivities.ui.screens.components.ErrorScreen
import com.example.shhsactivities.ui.screens.components.LoadingScreen
import com.example.shhsactivities.ui.screens.components.MiniClubItem
import com.example.shhsactivities.ui.states.ClubsRetrievalState
import com.example.shhsactivities.ui.theme.Typography
import com.example.shhsactivities.ui.viewmodels.HomeViewModel
import java.util.Date

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    onClickClub: (Club) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val user = viewModel.user.collectAsState().value
    val userClubs = viewModel.userClubs.collectAsState().value

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        val listState = rememberLazyListState()
        val isFirstVisible = remember { derivedStateOf { listState.firstVisibleItemIndex > 0 } }

        LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxSize()
        ) {
            stickyHeader {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.DarkGray)
                        .then(Modifier.statusBarsPadding())
                        .padding(8.dp)
                ) {
                    AnimatedContent(
                        targetState = isFirstVisible.value, label = "Header"
                    ) { isFirstVisible ->
                        if (isFirstVisible) {
                            Box {
                                Text(
                                    text = "Home",
                                    style = Typography.titleMedium,
                                    color = Color.White,
                                    modifier = Modifier.align(Alignment.Center)
                                )
                            }
                        } else {
                            Column {
                                Text(
                                    text = "Home",
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
                        }
                    }
                }
            }

            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.LightGray)
                        .padding(6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "My Clubs",
                        fontWeight = FontWeight.Bold,
                        style = Typography.titleLarge,
                        modifier = Modifier.weight(1f)
                    )
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = Icons.Default.Search.name,
                        modifier = Modifier.clickable {/*TODO*/ },
                        tint = Color.Black
                    )
                }
            }

            item {
                when(userClubs) {
                    is ClubsRetrievalState.Success -> {
                        LazyRow(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(120.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            items(userClubs.clubs) {
                                MiniClubItem(club = it, onClick = {
                                    //TODO add
                                })
                            }
                        }
                    }
                    ClubsRetrievalState.Error -> ErrorScreen(modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp)
                        .scale(0.6f), error = "loading clubs")
                    ClubsRetrievalState.Loading -> LoadingScreen(modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp))
                }
            }

            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.LightGray)
                        .padding(6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Recent Announcements",
                        fontWeight = FontWeight.Bold,
                        style = Typography.titleLarge,
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            when(userClubs) {
                is ClubsRetrievalState.Success -> {
                    items(userClubs.clubs) { club ->
                        var dropDownEnabled by remember { mutableStateOf(false) }

                        DropDownMenu(
                            isExpanded = dropDownEnabled,
                            onDropDownArrowClick = { dropDownEnabled = !dropDownEnabled },
                            title = club.name,
                            color = club.category.color
                        ) {
                            viewModel.recentAnnouncementsFromClub(club).forEach { announcement ->
                                AnnouncementItem(
                                    announcement = announcement,
                                    modifier = Modifier
                                        .background(Color.White)
                                        .padding(8.dp),
                                    onClickAnnouncement = { /* TODO */ }
                                )
                            }
                        }
                    }
                }
                ClubsRetrievalState.Error -> item {ErrorScreen(modifier = Modifier
                    .fillMaxSize()
                    .scale(0.6f), error = "loading announcements") }
                ClubsRetrievalState.Loading -> item { LoadingScreen(modifier = Modifier
                    .fillMaxSize()) }
            }
        }
    }
}
