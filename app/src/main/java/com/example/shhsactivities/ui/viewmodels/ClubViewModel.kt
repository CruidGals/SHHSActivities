package com.example.shhsactivities.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shhsactivities.data.models.Announcement
import com.example.shhsactivities.data.models.Club
import com.example.shhsactivities.data.models.ClubCategory
import com.example.shhsactivities.data.models.UserData
import com.example.shhsactivities.data.models.unknownUser
import com.example.shhsactivities.data.repositories.ClubRepository
import com.example.shhsactivities.ui.states.ClubRetrievalState
import com.google.firebase.firestore.toObject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class ClubViewModel @Inject constructor(
    private val clubRepository: ClubRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _clubState = MutableStateFlow<ClubRetrievalState>(ClubRetrievalState.Loading)
    val clubState = _clubState.asStateFlow()

    var club: Club? by mutableStateOf(Club())
        private set

    var clubId by mutableStateOf("")
        private set

    var clubAdministrators by mutableStateOf(listOf<UserData>())
        private set
    var clubMembers by mutableStateOf(listOf<UserData>())
        private set

    init {
        savedStateHandle.get<String>("clubId")?.let { id ->
            viewModelScope.launch {
                club = clubRepository.getClubById(id)?.let { clubRepository.toObject(it) } ?: Club()
                clubId = id

                _clubState.value = if(club == null) {
                    ClubRetrievalState.Error
                } else {
                    ClubRetrievalState.Success(club!!)
                }

                clubAdministrators = club!!.administrators.map {
                    it?.get()?.result?.toObject<UserData>() ?: unknownUser
                }

                clubMembers = club!!.members.map {
                    it?.get()?.result?.toObject<UserData>() ?: unknownUser
                }
            }
        }
    }
}

private val testuser = UserData(
    uid = "BlaBlaBla",
    email = "kchiem30@gmail.com",
    username = "Kyle Chiem",
    profilePictureUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/2/2b/USMC-06868.jpg/1280px-USMC-06868.jpg",
    phone = "123-456-7890"
)

private val testMembers = listOf(testuser) + List(15) {
    UserData(
        uid = "asldjsalkdas",
        email = "bruh@gmail.com",
        username = "Bagel Cream Cheese",
        profilePictureUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/2/2b/USMC-06868.jpg/1280px-USMC-06868.jpg",
        phone = "245-293-1238"
    )
}
