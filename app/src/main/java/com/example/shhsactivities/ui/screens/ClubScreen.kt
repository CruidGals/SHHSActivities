package com.example.shhsactivities.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.shhsactivities.ui.screens.components.AnnouncementItem
import com.example.shhsactivities.ui.screens.components.ClubMemberItem
import com.example.shhsactivities.ui.screens.components.ErrorScreen
import com.example.shhsactivities.ui.components.general.PopupBox
import com.example.shhsactivities.ui.states.ClubRetrievalState
import com.example.shhsactivities.ui.theme.Typography
import com.example.shhsactivities.ui.viewmodels.ClubViewModel

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ClubScreen(
    onClickBack: () -> Unit,
    viewModel: ClubViewModel = hiltViewModel()
) {
    val clubState = viewModel.clubState.collectAsState().value

    var goalPopup by remember { mutableStateOf(false) }
    var rosterPopup by remember { mutableStateOf(false) }

    when (clubState) {
        is ClubRetrievalState.Success -> {
            val club = clubState.club

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(club.category.color)
            ) {
                val listState = rememberLazyListState()
                val isFirstVisible =
                    remember { derivedStateOf { listState.firstVisibleItemIndex > 1 } }

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
                        ) {
                            IconButton(
                                onClick = { onClickBack() },
                                modifier = Modifier.align(Alignment.Start)
                            ) {
                                Icon(
                                    Icons.Default.KeyboardArrowLeft,
                                    Icons.Default.KeyboardArrowLeft.name,
                                    tint = Color.White,
                                    modifier = Modifier.scale(1.5f)
                                )
                            }
                        }

                        AnimatedContent(
                            targetState = isFirstVisible.value,
                            label = "header"
                        ) { isFirstVisible ->
                            if (isFirstVisible) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(club.category.color)
                                ) {

                                    Text(
                                        text = "Announcements",
                                        modifier = Modifier
                                            .align(Alignment.Center)
                                            .padding(4.dp),
                                        fontWeight = FontWeight.Bold,
                                        style = Typography.bodyLarge
                                    )

                                    Divider(
                                        color = Color.Black,
                                        modifier = Modifier.align(Alignment.BottomStart)
                                    )
                                }
                            }
                        }

                    }

                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            AsyncImage(
                                model = club.imageUrl,
                                contentDescription = "Club Image",
                                modifier = Modifier
                                    .size(160.dp)
                                    .padding(10.dp)
                                    .clip(CircleShape),
                                contentScale = ContentScale.Crop
                            )

                            Column(
                                modifier = Modifier.weight(0.5f),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = club.name,
                                    fontWeight = FontWeight.SemiBold,
                                    style = Typography.headlineLarge
                                )
                                Text(
                                    text = "Room ${club.room ?: ""}",
                                    fontWeight = FontWeight.Light,
                                    style = Typography.bodyMedium
                                )
                                Text(
                                    text = "Meets ${club.meetingFrequency ?: ""}",
                                    fontWeight = FontWeight.Light,
                                    style = Typography.bodyMedium
                                )

                                Row {
                                    Button(
                                        onClick = { goalPopup = true },
                                        modifier = Modifier.padding(horizontal = 4.dp)
                                    ) {
                                        Text(text = "Goal")
                                    }
                                    Button(
                                        onClick = { rosterPopup = true },
                                        modifier = Modifier.padding(horizontal = 4.dp)
                                    ) {
                                        Text(text = "Roster")
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.padding(8.dp))
                        }
                    }

                    item {
                        Box(modifier = Modifier.fillMaxWidth()) {

                            Text(
                                text = "Announcements",
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .padding(4.dp),
                                fontWeight = FontWeight.Bold,
                                style = Typography.bodyLarge
                            )

                            Divider(
                                color = Color.Black,
                                modifier = Modifier.align(Alignment.BottomStart)
                            )
                        }
                    }

                    items(club.announcements) { announcement ->
                        AnnouncementItem(
                            announcement = announcement,
                            onClickAnnouncement = {},
                            modifier = Modifier
                                .background(Color.White)
                                .padding(8.dp)
                        )
                    }
                }

                PopupBox(
                    width = 300f,
                    height = 200f,
                    isShown = goalPopup,
                    modifier = Modifier
                        .fillMaxSize()
                        .align(Alignment.Center),
                    onOutsideClick = { goalPopup = false })
                {
                    Text (
                        text = club.description ?: "",
                        modifier = Modifier.padding(40.dp),
                        style = Typography.headlineSmall
                    )
                }

                PopupBox(
                    width = 350f,
                    height = 500f,
                    isShown = rosterPopup,
                    modifier = Modifier
                        .fillMaxSize()
                        .align(Alignment.Center),
                    onOutsideClick = { rosterPopup = false })
                {
                    val memberListState = rememberLazyListState()
                    LazyColumn(
                        state = memberListState
                    ) {
                        item {
                            Spacer(modifier = Modifier.padding(vertical = 10.dp))
                            Column(modifier = Modifier.padding(horizontal = 10.dp)) {
                                Text(
                                    text = "Leaders",
                                    style = Typography.headlineMedium
                                )
                                Divider()
                            }
                        }

                        items(viewModel.clubAdministrators) {user ->
                            ClubMemberItem(user = user, onClickContact = { /*TODO*/ }, modifier = Modifier.fillMaxWidth())
                        }

                        item {
                            Spacer(modifier = Modifier.padding(vertical = 20.dp))
                            Column(modifier = Modifier.padding(horizontal = 10.dp)) {
                                Text(
                                    text = "Members",
                                    style = Typography.headlineMedium
                                )
                                Divider()
                            }
                        }
                        items(viewModel.clubMembers.filter { it !in viewModel.clubAdministrators }) { user ->
                            ClubMemberItem(user = user, onClickContact = { /*TODO*/ }, modifier = Modifier.fillMaxWidth())
                        }
                    }
                }
            }
        }

        is ClubRetrievalState.Error -> ErrorScreen("loading club")
        is ClubRetrievalState.Loading -> TODO()
    }
}