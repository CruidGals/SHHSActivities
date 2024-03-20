package com.example.shhsactivities.ui.states

import com.example.shhsactivities.data.models.Club

sealed interface ClubRetrievalState {

    data class Success(val club: Club): ClubRetrievalState

    data object Loading: ClubRetrievalState

    data object Error : ClubRetrievalState

}
