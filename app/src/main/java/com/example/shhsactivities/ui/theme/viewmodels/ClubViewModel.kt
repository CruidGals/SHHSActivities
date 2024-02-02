package com.example.shhsactivities.ui.theme.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shhsactivities.data.Club
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClubViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
): ViewModel() {
    var club = mutableStateOf<Club?>(null)

    init {
        savedStateHandle.get<Int>("clubId")?.let { id ->
            viewModelScope.launch {
                //get club by ID through repository given by FireBase
            }
        }
    }
}

sealed class ClubScreenEvent {

}