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
import com.example.shhsactivities.ui.components.general.SearchBar
import com.example.shhsactivities.ui.screens.components.ClubCatalogItem

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CatalogScreen(
    onClickClub: (club: Club) -> Unit,
    viewModel: CatalogViewModel = hiltViewModel()
) {
    val searchQuery = viewModel.searchQuery.collectAsState().value
    val queriedClubs = viewModel.clubsQueried.collectAsState().value
    val focusRequester = remember{FocusRequester()}

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        val listState = rememberLazyListState()

        LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxSize()
        ) {
            stickyHeader {
                SearchBar(
                    query = searchQuery,
                    onQueryTextChange = { viewModel.editSearchQuery(it)},
                    placeHolderText = "Search for clubs",
                    onCanceledClick = {},
                    focusRequester = focusRequester,
                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 10.dp)
                )
            }

            items(queriedClubs.clubs) { club ->
                ClubCatalogItem(club = club) { onClickClub(it) }
            }
        }
    }
}