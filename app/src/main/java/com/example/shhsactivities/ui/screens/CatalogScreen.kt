package com.example.shhsactivities.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.shhsactivities.data.models.Club
import com.example.shhsactivities.ui.components.OrderType
import com.example.shhsactivities.ui.components.OrderDirection
import com.example.shhsactivities.ui.components.general.ClubOrderRow
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
    val searchOrder = viewModel.searchOrder.collectAsState().value
    val queriedClubs = viewModel.clubsQueried.collectAsState().value
    val focusRequester = remember{FocusRequester()}

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        LazyColumn(
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

                ClubOrderRow(
                    order = searchOrder,
                    onTitleFilterClick = { viewModel.editSearchOrder(OrderType.Title(if(searchOrder.direction is OrderDirection.Ascending) OrderDirection.Descending else OrderDirection.Ascending)) },
                    onCategoryClick = { category ->
                        viewModel.editSearchOrder(
                            searchOrder.copy(
                                searchOrder.direction,
                                if(category in searchOrder.categories) searchOrder.categories.filter { it != category } else searchOrder.categories + category
                            )
                        )
                    },
                    modifier = Modifier.padding(2.dp)
                )
            }

            items(queriedClubs.clubs) { club ->
                ClubCatalogItem(club = club) { onClickClub(it) }
            }
        }
    }
}