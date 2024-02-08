package com.example.shhsactivities.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shhsactivities.data.Announcement
import com.example.shhsactivities.data.Club
import com.example.shhsactivities.ui.events.UiEvent
import com.example.shhsactivities.ui.states.AnnouncementsRetrievalState
import com.example.shhsactivities.ui.states.ClubRetrievalState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClubViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
): ViewModel() {

    data class ClubUIState(
        val clubState: ClubRetrievalState = ClubRetrievalState.Loading,
        val announcementState: AnnouncementsRetrievalState = AnnouncementsRetrievalState.Loading,
        val clubId: String= ""
    )

    var clubUiState by mutableStateOf(ClubUIState())
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    var club = mutableStateOf<Club?>(null)

    init {
        savedStateHandle.get<Int>("clubId")?.let { id ->
            viewModelScope.launch {
                //get club by ID through repository given by FireBase
            }
        }
    }

    fun queryClubById(id: String) = viewModelScope.launch {
        try {
            val club: Club = Club() //TODO Call from backend of repository
            clubUiState = clubUiState.copy(
                clubState = ClubRetrievalState.Success(club),
                clubId = club.id
            )
        }
    }

    fun onEvent(event: ClubScreenEvent) {
        when (event) {
            is ClubScreenEvent.OnAnnouncementClick -> TODO()
            is ClubScreenEvent.OnClubPictureClick -> TODO()
            is ClubScreenEvent.OnGoalClick -> TODO()
            is ClubScreenEvent.OnRosterClick -> TODO()
        }
    }

    private fun sendUiEvent(event: UiEvent){
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}

sealed class ClubScreenEvent {
    data object OnClubPictureClick: ClubScreenEvent()
    data object OnGoalClick: ClubScreenEvent()
    data object OnRosterClick: ClubScreenEvent()
    data class OnAnnouncementClick(val announcement: Announcement): ClubScreenEvent()
}