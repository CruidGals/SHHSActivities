package com.example.shhsactivities.ui.states

import com.example.shhsactivities.data.models.Club

sealed interface ClubsRetrievalState {

    data class Success(val clubs: List<Club>): ClubsRetrievalState

    data object Loading: ClubsRetrievalState

    data object Error: ClubsRetrievalState

}
