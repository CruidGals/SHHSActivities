package com.example.shhsactivities.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shhsactivities.data.GoogleAuthApi
import com.example.shhsactivities.data.models.UserData
import com.example.shhsactivities.data.models.unknownUser
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
    googleAuthApi: GoogleAuthApi
): ViewModel() {

    private val _user = MutableStateFlow<UserRetrievalState>(UserRetrievalState.Loading)
    val user = _user.asStateFlow()

    init {
        viewModelScope.launch {
            val userSnapshot = userRepository.getUser(googleAuthApi.getSignedInUser()?.uid ?: "1aBcDeFg")
            _user.value = userSnapshot?.toObject(UserData::class.java)
                ?.let { UserRetrievalState.Success(it) }
                ?: UserRetrievalState.Success(unknownUser)
        }
    }
}