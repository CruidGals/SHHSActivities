package com.example.shhsactivities.ui.states

import com.example.shhsactivities.data.models.UserData

sealed interface GoogleApiState {

    data class Success(val userData: UserData?): GoogleApiState

    data class Error(val e: Exception): GoogleApiState
}