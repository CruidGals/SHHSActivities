package com.example.shhsactivities.ui.screens

import android.util.Log
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.shhsactivities.data.models.Club
import com.example.shhsactivities.ui.theme.Typography
import com.example.shhsactivities.ui.viewmodels.CatalogViewModel
import com.example.shhsactivities.ui.viewmodels.components.general.SearchBar

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CatalogScreen(
    onClickClub: (club: Club) -> Unit,
    viewModel: CatalogViewModel = hiltViewModel()
) {
    var searchQuery = viewModel.searchQuery.collectAsState().value
    var queriedClubs = viewModel.clubsQueried.collectAsState().value
    val focusRequester = remember{FocusRequester()}

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

                                /*IconButton(
                                    modifier = Modifier.align(Alignment.CenterEnd),
                                    onClick = { onSearchClick() }
                                ) {
                                    Icon(
                                        Icons.Default.Search,
                                        contentDescription = Icons.Default.Search.name,
                                        tint = Color.White
                                    )
                                }*/
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
                SearchBar(
                    query = searchQuery,
                    onQueryTextChange = { viewModel.editSearchQuery(it)},
                    placeHolderText = "Search for clubs",
                    onCanceledClick = {},
                    focusRequester = focusRequester,
                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 10.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewCatalogScreen() {
    CatalogScreen( onClickClub = {})
}
