package com.example.shhsactivities.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shhsactivities.data.models.Club
import com.example.shhsactivities.data.models.ClubCategory
import com.example.shhsactivities.data.repositories.ClubRepository
import com.example.shhsactivities.ui.states.ClubsRetrievalState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
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

    //TODO add filter flow

    private val _clubsQueried = MutableStateFlow(ClubsRetrievalState.Success(allClubs))
    val clubsQueried = searchQuery
        .combine(_clubsQueried) { text, clubs ->
            if (text.isBlank()) {
                clubs
            }
            else {
                ClubsRetrievalState.Success(
                    clubs.clubs.filter {
                        it.name.contains(text, ignoreCase = true)
                    }
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
                it.toObject(Club::class.java)!!
            }
        }
        _allClubs = testClubs //TESTING

        _clubsQueried.value = ClubsRetrievalState.Success(allClubs)
    }

    fun editSearchQuery(query: String) {
        _searchQuery.value = query
        Log.d("Catalog", _clubsQueried.value.clubs.size.toString())
    }
}

val testClubs = listOf(
    Club(
        name = "Horror Club",
        room = "345",
        meetingFrequency = "Every Monday",
        category = ClubCategory.HOBBY_AND_SPECIAL_INTERESTS
    ),
    Club(
        name = "Anime Club",
        room = "169",
        meetingFrequency = "Every Day!!",
        category = ClubCategory.HOBBY_AND_SPECIAL_INTERESTS
    ),
    Club(
        name = "Basketball Club",
        room = "Gym",
        meetingFrequency = "Every Tuesday and Thursday",
        category = ClubCategory.ATHLETICS
    )
)