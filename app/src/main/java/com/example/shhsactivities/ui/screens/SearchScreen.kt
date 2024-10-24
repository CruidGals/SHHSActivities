package com.example.shhsactivities.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import com.example.shhsactivities.ui.screens.components.ClubItem
import com.example.shhsactivities.ui.screens.components.ErrorScreen
import com.example.shhsactivities.ui.screens.components.LoadingScreen
import com.example.shhsactivities.ui.states.ClubsRetrievalState
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CatalogScreen(
    onClickClub: (clubId: String) -> Unit,
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
        Column {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.LightGray)
                    .padding(bottom = 5.dp)
            ) {
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

            when(queriedClubs) {
                is ClubsRetrievalState.Success -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(queriedClubs.clubs) { club ->
                            ClubItem(club = club) {
                                onClickClub(viewModel.retrieveIdFromClub(it) ?: "-1")
                            }
                        }
                    }
                }
                ClubsRetrievalState.Error -> ErrorScreen(modifier = Modifier.fillMaxSize(), error = "loading clubs")
                ClubsRetrievalState.Loading -> LoadingScreen(modifier = Modifier.fillMaxSize())
            }
        }
    }
}