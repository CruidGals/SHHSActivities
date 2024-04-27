package com.example.shhsactivities.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shhsactivities.data.models.Club
import com.example.shhsactivities.data.models.ClubCategory
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
    private val clubRepository: ClubRepository
): ViewModel() {
    private var _allClubs: List<Club> = emptyList()
    val allClubs get() = _allClubs

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val _searchOrder = MutableStateFlow<OrderType>(OrderType.Title(OrderDirection.Ascending))
    val searchOrder = _searchOrder.asStateFlow()

    private val _clubsQueried = MutableStateFlow(ClubsRetrievalState.Success(allClubs))
    val clubsQueried = combine(_searchQuery, _clubsQueried, _searchOrder) { text, clubs, order ->
        if (text.isBlank()) {
            clubs
        }
        else {
            val filteredClubs = clubs.clubs.filter {
                it.name.contains(text, ignoreCase = true)
            }

            ClubsRetrievalState.Success(
                filteredClubs
            )
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        _clubsQueried.value
    )

    init {
        viewModelScope.launch {
            _allClubs = clubRepository.getAllClubs().map {
                clubRepository.toObject(it)
            }

            _clubsQueried.value = ClubsRetrievalState.Success(allClubs)
        }
    }

    fun editSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun editSearchOrder(order: OrderType) {
        _searchOrder.value = order
    }
}