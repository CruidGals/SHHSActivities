package com.example.shhsactivities.ui.viewmodels

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shhsactivities.data.models.UserData
import com.example.shhsactivities.data.repositories.UserRepository
import com.example.shhsactivities.ui.states.GoogleApiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val userRepository: UserRepository
): ViewModel() {
    data class SignInState(
        val isSignInSuccessful: Boolean = false,
        val userData: UserData? = null,
        val signInError: String? = null
    )

    private val _state = MutableStateFlow(SignInState())
    val state = _state.asStateFlow()

    fun onSignInResult(result: GoogleApiState) {
        when(result) {
            is GoogleApiState.Success -> {
                _state.update {
                    SignInState(
                        isSignInSuccessful = true,
                        userData = result.userData
                    )
                }
            }
            is GoogleApiState.Error -> {
                _state.update {
                    SignInState(
                        isSignInSuccessful = false,
                        signInError = result.e.toString()
                    )
                }
            }
        }

        viewModelScope.launch {
            state.value.userData?.let { userRepository.addUser(it) }
        }
    }

    fun resetState() = _state.update {SignInState()}
}