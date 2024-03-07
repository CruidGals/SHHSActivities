package com.example.shhsactivities.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.shhsactivities.data.models.Club
import com.example.shhsactivities.data.repositories.ClubRepository
import com.example.shhsactivities.ui.theme.Typography
import com.example.shhsactivities.ui.viewmodels.CatalogViewModel
import com.example.shhsactivities.ui.viewmodels.ClubViewModel

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun CatalogScreen(
    onSearchClick: () -> Unit,
    onClickClub: (club: Club) -> Unit,
    //viewModel: CatalogViewModel = hiltViewModel()
) {
    /*val state = viewModel.state.collectAsStateWithLifecycle().value
    var shownClubs by remember { mutableStateOf(viewModel.getShownClubs()) } //IDK

    LaunchedEffect(key1 = state.clubsState) {
        shownClubs = viewModel.getShownClubs()
    }*/

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        val listState = rememberLazyListState()
        val isFirstVisible = remember {
            derivedStateOf { listState.firstVisibleItemIndex > 0 }
        }

        LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxSize()
        ) {
            stickyHeader {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Gray)
                        .padding(3.dp)
                ) {
                    AnimatedContent(
                        targetState = isFirstVisible.value, label = "Catalog"
                    ) { isFirstVisible ->
                        if (isFirstVisible) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 5.dp)
                            ) {
                                Text(
                                    modifier = Modifier.align(Alignment.CenterStart),
                                    text = "Catalog",
                                    style = Typography.headlineSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )

                                IconButton(
                                    modifier = Modifier.align(Alignment.CenterEnd),
                                    onClick = { onSearchClick() }
                                ) {
                                    Icon(
                                        Icons.Default.Search,
                                        contentDescription = Icons.Default.Search.name,
                                        tint = Color.White
                                    )
                                }
                            }
                        } else {
                            Column(
                                modifier = Modifier.padding(
                                    top = 30.dp,
                                    start = 5.dp,
                                    bottom = 5.dp
                                ),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "Catalog",
                                    style = Typography.headlineLarge,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            }
                        }
                    }
                }
            }

            item {

            }
        }
    }
}

@Preview
@Composable
fun PreviewCatalogScreen() {
    CatalogScreen(onSearchClick = { /*TODO*/ }, onClickClub = {})
}

@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Jawn() {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        SearchBar(
            query = "",
            onQueryChange = {},
            onSearch = {},
            active = true,
            onActiveChange = {},
            placeholder = {
                Text(text = "hi")
            }
        ) {Text(text = "Hi")}
        Spacer(modifier = Modifier.padding())

    }
}
