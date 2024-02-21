package com.example.shhsactivities.data.models

import android.media.Image
import com.google.firebase.firestore.DocumentReference
import java.util.Date

data class Club(
    var name: String = "",
    var imageUrl: String? = "",
    var room: String? = "",
    var meetingFrequency: String? = "",
    var administrators: List<DocumentReference?> = listOf(),
    var members: List<DocumentReference?> = listOf(),
    var announcements: List<Announcement> = listOf()
)

/*
    DocumentReference next to user will refer to the "users"
    collection in firebase
 */
data class Announcement(
    var user: DocumentReference? = null,
    var date: Date = Date(),
    var description: String = "", //May be a typeface object
    var pinned: Boolean = false
)
