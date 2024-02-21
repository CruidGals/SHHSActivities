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
