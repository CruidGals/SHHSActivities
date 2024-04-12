package com.example.shhsactivities.ui.viewmodels

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shhsactivities.data.GoogleAuthApi
import com.example.shhsactivities.data.models.Club
import com.example.shhsactivities.data.models.ClubCategory
import com.example.shhsactivities.data.models.UserData
import com.example.shhsactivities.data.repositories.ClubRepository
import com.example.shhsactivities.data.repositories.UserRepository
import com.example.shhsactivities.ui.states.ClubsRetrievalState
import com.example.shhsactivities.ui.components.ClubOrder
import com.example.shhsactivities.ui.components.OrderDirection
import com.google.firebase.firestore.toObject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val clubRepository: ClubRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val _clubsQueried = MutableStateFlow(ClubsRetrievalState.Success(listOf()))
    val clubsQueried = searchQuery
        .combine(_clubsQueried) { text, clubs ->
            if (text.isBlank()) {
                clubs
            }
            else {
                ClubsRetrievalState.Success(
                    queryClubsByOrder()
                )
            }
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            _clubsQueried.value
        )

    private val user = savedStateHandle.getLiveData<UserData>("user").value

    init {
        viewModelScope.launch {
            val userReference = user?.let { userRepository.getUser(it.uid)?.reference }
            val userClubsSnapshot = userReference?.let { clubRepository.getClubsByMember(it) }
            _clubsQueried.value = ClubsRetrievalState.Success(
                userClubsSnapshot?.map { club ->
                    club.toObject<Club>() ?: Club()
                } ?: listOf()
            )


            //TESTING
            _clubsQueried.value = ClubsRetrievalState.Success(testClubs)
        }
    }

    //TODO Include this function in teh above combine function
    fun queryClubsByOrder(order: ClubOrder = ClubOrder.Title(OrderDirection.Ascending)): List<Club> {
        var clubs: List<Club> = listOf()

        viewModelScope.launch {
            clubs = listOf() //TODO Repository
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

        return clubs
    }

    fun editSearchQuery(query: String) {
        _searchQuery.value = query
        Log.d("Catalog", _clubsQueried.value.clubs.size.toString())
    }
}

private val testClubs = listOf(
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