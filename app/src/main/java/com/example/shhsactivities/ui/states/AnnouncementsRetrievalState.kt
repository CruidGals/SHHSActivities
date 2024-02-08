package com.example.shhsactivities.ui.states

import com.example.shhsactivities.data.Announcement

sealed interface AnnouncementsRetrievalState {

    data class Success(val announcements: List<Announcement>): AnnouncementsRetrievalState

    data object Loading: AnnouncementsRetrievalState

    data object Error: AnnouncementsRetrievalState

}
