package com.example.shhsactivities.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.shhsactivities.ui.states.ClubRetrievalState
import com.example.shhsactivities.ui.viewmodels.ClubViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ClubScreen(
    viewModel: ClubViewModel = hiltViewModel()
) {
    val clubState = viewModel.clubState.collectAsState().value

    when(clubState) {
        is ClubRetrievalState.Success -> {
            val club = clubState.club

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(club.category.color)
            ) {
                val listState = rememberLazyListState()
                val

                LazyColumn(
                    state = listState,
                    modifier = Modifier.fillMaxSize()
                ) {
                    stickyHeader {

                    }
                }
            }
        }
        is ClubRetrievalState.Error -> TODO()
        is ClubRetrievalState.Loading -> TODO()
    }
}