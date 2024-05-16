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

fun toModel(proto: com.example.shhsactivities.UserData): UserData {
    return UserData(
        uid = proto.uid,
        email = proto.email,
        username = proto.username,
        profilePictureUrl = proto.profilePictureUrl,
        phone = proto.phone,
        isAdmin = proto.isAdmin
    )
}

fun toProto(model: UserData): com.example.shhsactivities.UserData {
    return com.example.shhsactivities.UserData.newBuilder()
        .setUid(model.uid)
        .setEmail(model.email ?: "")
        .setUsername(model.username ?: "")
        .setProfilePictureUrl(model.profilePictureUrl ?: "")
        .setPhone(model.phone ?: "")
        .setIsAdmin(model.isAdmin)
        .build()
}
