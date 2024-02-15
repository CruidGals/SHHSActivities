package com.example.shhsactivities.data.models

import android.media.Image
import java.util.Date

data class Club(
    var name: String,
    var image: Image,
    var room: String,
    var meetingFrequency: String,
    var administrators: List<UserData>,
    var members: List<UserData>,
    var announcements: List<Announcement>,
    var id: String
)

data class Announcement(
    var user: UserData,
    var dateAndTime: Date,
    var description: String, //May be a typeface object
    var pinned: Boolean = false
)
