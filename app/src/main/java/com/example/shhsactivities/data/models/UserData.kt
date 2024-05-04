package com.example.shhsactivities.data.models

/*
    Grabs information from google account and
    stores it into this class
 */
data class UserData(
    val uid: String = "",
    val email: String? = null,
    val username: String? = null,
    val profilePictureUrl: String? = null,
    val phone: String? = null,
    val isAdmin: Boolean = false
)

val unknownUser = UserData (
    uid = "",
    username = "Unknown User",
    profilePictureUrl = "https://upload.wikimedia.org/wikipedia/commons/b/bc/Unknown_person.jpg"
)
