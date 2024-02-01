package com.example.shhsactivities.data

data class Person(
    var firstName: String,
    var lastName: String,
    var email: String,
    var phone: String?,
    var isAdmin: Boolean = false
)
