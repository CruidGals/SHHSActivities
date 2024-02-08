package com.example.shhsactivities.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.shhsactivities.ui.states.ClubsRetrievalState
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class HomeViewModel: ViewModel() {

    data class HomeUIState(
        val clubsState: ClubsRetrievalState = ClubsRetrievalState.Loading
    )

    var homeUIState by mutableStateOf(HomeUIState())
        private set

    fun queryClubsByOrder() {

    }
}

sealed class HomeScreenEvent {

}