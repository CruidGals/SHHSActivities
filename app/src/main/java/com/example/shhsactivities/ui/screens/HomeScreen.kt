package com.example.shhsactivities.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.shhsactivities.data.models.Club
import com.example.shhsactivities.ui.screens.components.ClubCatalogItem
import com.example.shhsactivities.ui.theme.Typography
import com.example.shhsactivities.ui.viewmodels.HomeViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    onClickClub: (Club) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val queriedClubs = viewModel.clubsQueried.collectAsState().value
    val searchQuery = viewModel.searchQuery.collectAsState().value

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
                                    text = "My Clubs",
                                    style = Typography.titleMedium,
                                    color = Color.White,
                                    modifier = Modifier.align(Alignment.Center)
                                )

                                IconButton(
                                    onClick = { /*TODO add floating search bar*/ },
                                    modifier = Modifier.align(Alignment.CenterEnd)
                                ) {
                                    Icon(
                                        Icons.Default.Search,
                                        Icons.Default.Search.name,
                                        tint = Color.White
                                    )
                                }
                            }
                        } else {
                            Column {
                                Text(
                                    text = "My Clubs",
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

            items(queriedClubs.clubs) { club ->
                ClubCatalogItem(club = club) { onClickClub(it) }
            }
        }
    }
}