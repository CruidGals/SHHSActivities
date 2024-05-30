package com.example.shhsactivities.ui.viewmodels

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shhsactivities.data.GoogleAuthApi
import com.example.shhsactivities.data.models.Announcement
import com.example.shhsactivities.data.models.Club
import com.example.shhsactivities.data.models.ClubCategory
import com.example.shhsactivities.data.models.UserData
import com.example.shhsactivities.data.models.toModel
import com.example.shhsactivities.data.models.unknownUser
import com.example.shhsactivities.data.repositories.ClubRepository
import com.example.shhsactivities.data.repositories.UserPreferencesRepository
import com.example.shhsactivities.data.repositories.UserRepository
import com.example.shhsactivities.ui.states.ClubsRetrievalState
import com.example.shhsactivities.ui.components.OrderType
import com.example.shhsactivities.ui.components.OrderDirection
import com.example.shhsactivities.ui.states.UserRetrievalState
import com.google.firebase.firestore.auth.User
import com.google.firebase.firestore.toObject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.Calendar
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val clubRepository: ClubRepository,
    private val userPreferencesRepository: UserPreferencesRepository
): ViewModel() {

    private val _user = MutableStateFlow<UserRetrievalState>(UserRetrievalState.Loading)
    val user = _user.asStateFlow()

    private val _userClubs = MutableStateFlow<ClubsRetrievalState>(ClubsRetrievalState.Loading)
    val userClubs = _userClubs.asStateFlow()

    //When accessing search bar, it requires a list of club Ids.
    private var userClubsIds: List<String>? = null

    init {
        viewModelScope.launch {
            val userData = toModel(userPreferencesRepository.getUserData())
            _user.value = UserRetrievalState.Success(userData)
            val userSnapshot = userRepository.getUser(userData.uid)
            val userRef = userSnapshot?.reference

            val userClubsSnapshot = userSnapshot?.reference?.let { clubRepository.getClubsByMember(it) }
            userClubsIds = userClubsSnapshot?.map { it.id }
            val clubs = userClubsSnapshot?.map { clubRepository.toObject(it) } ?: listOf()

            // Put clubs that the user is a leader in first, then other clubs.
            _userClubs.value = ClubsRetrievalState.Success(
                clubs.filter { userRef in it.administrators }
                    .sortedBy { it.name.lowercase() } +
                        clubs.filter { userRef !in it.administrators }
                            .sortedBy { it.name.lowercase() }
            )
        }
    }

    //Assuming "recent" means latest two announcements posted
    fun recentAnnouncementsFromClub(club: Club): List<Announcement> {
        return club.announcements.slice(IntRange(0, 1))
    }
}