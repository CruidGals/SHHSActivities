package com.example.shhsactivities.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shhsactivities.data.models.Club
import com.example.shhsactivities.data.repositories.ClubRepository
import com.example.shhsactivities.ui.states.ClubsRetrievalState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
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

    lateinit var allClubs: List<Club>
        private set

    private val _state = MutableStateFlow(CatalogUiState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            allClubs = clubRepository.getAllClubs().map {
                it.toObject(Club::class.java)!!
            }


            when (allClubs.size) {
                0 -> {
                    _state.update {
                        CatalogUiState(
                            clubsState = ClubsRetrievalState.Error
                        )
                    }
                }

                else -> {
                    _state.update {
                        CatalogUiState (
                            clubsState = ClubsRetrievalState.Success(
                                clubs = allClubs
                            )
                        )
                    }
                }
            }
        }
    }

    fun filterClubsBySearchQuery(query: String) {
        editSearchQuery(query)

        
    }

    fun getShownClubs(): List<Club> {
        return when(state.value.clubsState) {
            is ClubsRetrievalState.Success -> (state.value.clubsState as ClubsRetrievalState.Success).clubs
            else -> listOf<Club>()
        }
    }

    private fun editSearchQuery(query: String) {
        viewModelScope.launch {
            _state.update {
                CatalogUiState (
                    searchQuery = query
                )
            }
        }
    }
}