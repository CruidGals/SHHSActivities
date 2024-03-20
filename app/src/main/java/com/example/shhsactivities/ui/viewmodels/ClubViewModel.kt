package com.example.shhsactivities.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shhsactivities.data.models.Announcement
import com.example.shhsactivities.data.models.Club
import com.example.shhsactivities.data.repositories.ClubRepository
import com.example.shhsactivities.ui.states.ClubRetrievalState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClubViewModel @Inject constructor(
    private val clubRepository: ClubRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _clubState = MutableStateFlow<ClubRetrievalState>(ClubRetrievalState.Loading)
    val clubState = _clubState.asStateFlow()

    var club: Club? by mutableStateOf(Club())
        private set

    var clubId by mutableStateOf("")
        private set

    init {
        savedStateHandle.get<String>("clubId")?.let { id ->
            viewModelScope.launch {
                club = clubRepository.getClubById(id)?.toObject(Club::class.java)
                clubId = id

                _clubState.value = if(club == null) {
                    ClubRetrievalState.Error
                } else {
                    ClubRetrievalState.Success(club!!)
                }
            }
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
}

sealed class ClubScreenEvent {
    data object OnClubPictureClick: ClubScreenEvent()
    data object OnGoalClick: ClubScreenEvent()
    data object OnRosterClick: ClubScreenEvent()
    data class OnAnnouncementClick(val announcement: Announcement): ClubScreenEvent()
}