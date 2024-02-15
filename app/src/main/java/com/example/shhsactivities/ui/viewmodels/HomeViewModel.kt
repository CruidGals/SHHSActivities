package com.example.shhsactivities.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shhsactivities.data.models.Club
import com.example.shhsactivities.ui.states.ClubsRetrievalState
import com.example.shhsactivities.ui.viewmodels.components.ClubOrder
import com.example.shhsactivities.ui.viewmodels.components.OrderDirection
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel: ViewModel() {

    data class HomeUIState(
        val clubsState: ClubsRetrievalState = ClubsRetrievalState.Loading
    )

    var homeUIState by mutableStateOf(HomeUIState())
        private set

    fun queryClubsByOrder(order: ClubOrder) {
        viewModelScope.launch {
            val clubs: Flow<List<Club>> = flow{} //TODO Repository
            clubs.map { clubs ->
                when(order.direction) {
                    is OrderDirection.Ascending -> {
                        when(order) {
                            is ClubOrder.Leadership -> TODO()
                            is ClubOrder.Title -> TODO()
                        }
                    }
                    is OrderDirection.Descending -> {
                        when(order) {
                            is ClubOrder.Leadership -> TODO()
                            is ClubOrder.Title -> TODO()
                        }
                    }
                }
            }
        }
    }
}