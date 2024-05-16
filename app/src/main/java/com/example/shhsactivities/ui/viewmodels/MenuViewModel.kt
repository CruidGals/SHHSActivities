package com.example.shhsactivities.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shhsactivities.data.GoogleAuthApi
import com.example.shhsactivities.data.models.UserData
import com.example.shhsactivities.data.models.toModel
import com.example.shhsactivities.data.models.toProto
import com.example.shhsactivities.data.models.unknownUser
import com.example.shhsactivities.data.repositories.UserPreferencesRepository
import com.example.shhsactivities.data.repositories.UserRepository
import com.example.shhsactivities.ui.states.UserRetrievalState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/*
 * This ViewModel covers the MenuScreen, DisplayScreen
 * NotificationScreen, and ProfileScreen
 */
@HiltViewModel
class MenuViewModel @Inject constructor(
    userRepository: UserRepository,
    userPreferencesRepository: UserPreferencesRepository,
    googleAuthApi: GoogleAuthApi
): ViewModel() {

    private val _user = MutableStateFlow<UserRetrievalState>(UserRetrievalState.Loading)
    val user = _user.asStateFlow()

    init {
        viewModelScope.launch {
            /* val userSnapshot = userRepository.getUser(googleAuthApi.getSignedInUser()?.uid ?: "1aBcDeFg")
            val userData = userSnapshot?.toObject(UserData::class.java) ?: unknownUser
            _user.value = UserRetrievalState.Success(userData) */

            val userData = toModel(userPreferencesRepository.getUserData())
            _user.value = UserRetrievalState.Success(userData)

            Log.d("Menu", toModel(userPreferencesRepository.getUserData()).toString())
        }
    }
}