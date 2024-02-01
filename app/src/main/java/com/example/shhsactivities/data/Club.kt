package com.example.shhsactivities.data

import android.media.Image
import java.util.Date

data class Club(
    var name: String,
    var image: Image,
    var room: String,
    var meetingFrequency: String,
    var administrators: List<Person>,
    var members: List<Person>,
    var announcements: Announcement
)

data class Announcement(
    var user: Person,
    var dateAndTime: Date,
    var description: String, //May be a typeface object
    var pinned: Boolean = false
)
