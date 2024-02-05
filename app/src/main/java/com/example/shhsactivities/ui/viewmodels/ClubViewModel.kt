package com.example.shhsactivities.ui.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shhsactivities.data.Announcement
import com.example.shhsactivities.data.Club
import com.example.shhsactivities.ui.events.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClubViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
): ViewModel() {
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