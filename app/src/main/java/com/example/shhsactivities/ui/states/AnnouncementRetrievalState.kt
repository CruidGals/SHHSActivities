package com.example.shhsactivities.ui.states

import com.example.shhsactivities.data.Announcement

sealed interface AnnouncementRetrievalState {

    data class Success(val announcements: List<Announcement>): AnnouncementRetrievalState

    data object Loading: AnnouncementRetrievalState

    data object Error: AnnouncementRetrievalState

}
