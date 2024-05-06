package com.example.shhsactivities.ui.states

import com.example.shhsactivities.data.models.UserData

sealed interface UserRetrievalState {

    data class Success(val user: UserData): UserRetrievalState

    data object Loading: UserRetrievalState

    data object Error : UserRetrievalState

}