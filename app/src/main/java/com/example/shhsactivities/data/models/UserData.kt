package com.example.shhsactivities.data.models

data class UserData(
    val userId: String,
    val email: String?,
    val username: String?,
    val profilePictureUrl: String?,
    val phone: String?,
    val isAdmin: Boolean = false
)
