package com.example.shhsactivities.ui.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shhsactivities.data.models.Club
import com.example.shhsactivities.data.repositories.ClubRepository
import com.example.shhsactivities.ui.components.ClubOrder
import com.example.shhsactivities.ui.components.OrderType
import com.example.shhsactivities.ui.components.OrderDirection
import com.example.shhsactivities.ui.states.ClubsRetrievalState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CatalogViewModel @Inject constructor(
    private val clubRepository: ClubRepository,
    private val clubOrder: ClubOrder,
    savedStateHandle: SavedStateHandle
): ViewModel() {
    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val _searchOrder = MutableStateFlow<OrderType>(OrderType.Title(OrderDirection.Ascending))
    val searchOrder = _searchOrder.asStateFlow()

    private val _clubsQueried = MutableStateFlow<ClubsRetrievalState>(ClubsRetrievalState.Loading)
    val clubsQueried = combine(_searchQuery, _clubsQueried, _searchOrder) { text, clubs, order ->
        when(clubs) {
            is ClubsRetrievalState.Success -> {
                if (text.isBlank()) {
                    ClubsRetrievalState.Success(
                        clubOrder.getClubs(clubs.clubs, order)
                    )
                }
                else {
                    val searchFilteredClubs = clubs.clubs.filter { it.name.contains(text, ignoreCase = true) }

                    ClubsRetrievalState.Success(
                        clubOrder.getClubs(searchFilteredClubs, order)
                    )
                }
            }
            else -> clubs
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        _clubsQueried.value
    )

    /*
     * Meant to save some read/write calls to firebase by mapping clubs
     * to their respective clubIds.
     */
    private val _clubIdDict = MutableStateFlow<Map<Club, String>>(mapOf())

    init {
        viewModelScope.launch {
            savedStateHandle.get<String>("clubIds")?.let { clubIds ->
                //If no parameters for specific club Ids passed, retrieves all clubs in database
                if (clubIds.isBlank()) {
                    val clubsSnapshot = clubRepository.getAllClubs()
                    val allClubIds = clubsSnapshot.map { it.id }
                    val clubs = clubsSnapshot.map { clubRepository.toObject(it) }

                    _clubIdDict.value = clubs.zip(allClubIds).toMap()
                    _clubsQueried.value = ClubsRetrievalState.Success(clubs)
                } else {
                    val clubs = clubIds.split(",").map { id ->
                        val snapshot = clubRepository.getClubById(id)

                        //TODO If club isn't found, maybe remove?
                        snapshot?.let { clubRepository.toObject(it) } ?: Club()
                    }

                    _clubIdDict.value = clubs.zip(clubIds.split(",")).toMap()
                    _clubsQueried.value = ClubsRetrievalState.Success(clubs)
                }
            }
        }
    }

    fun retrieveIdFromClub(club: Club): String? = _clubIdDict.value[club]

    fun editSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun editSearchOrder(order: OrderType) {
        _searchOrder.value = order
    }
}