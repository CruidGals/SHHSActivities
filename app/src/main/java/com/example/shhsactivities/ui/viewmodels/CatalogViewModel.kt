package com.example.shhsactivities.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shhsactivities.data.models.Club
import com.example.shhsactivities.data.repositories.ClubRepository
import com.example.shhsactivities.ui.states.ClubsRetrievalState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CatalogViewModel @Inject constructor(
    private val clubRepository: ClubRepository
): ViewModel() {
    data class CatalogUiState (
        val clubsState: ClubsRetrievalState = ClubsRetrievalState.Loading,
        val searchQuery: String = ""
    )

    var catalogUiState by mutableStateOf(CatalogUiState())
        private set

    init {
        viewModelScope.launch {
            val clubs = clubRepository.getAllClubs()


            when (clubs.size) {
                0 -> {
                    catalogUiState = catalogUiState.copy(
                        clubsState = ClubsRetrievalState.Error
                    )
                }

                else -> {
                    catalogUiState = catalogUiState.copy(
                        clubsState = ClubsRetrievalState.Success(
                            clubs = clubs.map {
                                it.toObject(Club::class.java)!!
                            }
                        )
                    )
                }
            }
        }
    }

    fun filterClubsBySearchQuery(query: String) {
        editSearchQuery(query)

        
    }

    private fun editSearchQuery(query: String) {
        viewModelScope.launch {
            catalogUiState = catalogUiState.copy(
                searchQuery = query
            )
        }
    }
}