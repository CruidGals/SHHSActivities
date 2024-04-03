package com.example.shhsactivities.data.models

import android.media.Image
import androidx.compose.ui.graphics.Color
import com.example.shhsactivities.ui.theme.AcademicsColor
import com.example.shhsactivities.ui.theme.AffinityAndActivismColor
import com.example.shhsactivities.ui.theme.ArtsColor
import com.example.shhsactivities.ui.theme.AthleticsColor
import com.example.shhsactivities.ui.theme.CommunityAndServiceColor
import com.example.shhsactivities.ui.theme.HobbyAndSpecialInterestsColor
import com.example.shhsactivities.ui.theme.PublicationsColor
import com.example.shhsactivities.ui.theme.StudentGovernmentAndClassCabinetColor
import com.google.firebase.firestore.DocumentReference
import java.util.Date

data class Club(
    val name: String = "",
    val imageUrl: String? = "",
    val room: String? = "",
    val meetingFrequency: String? = "",
    val description: String? = "",
    val category: ClubCategory = ClubCategory.COMMUNITY_AND_SERVICE,
    val administrators: List<DocumentReference?> = listOf(),
    val members: List<DocumentReference?> = listOf(),
    val announcements: List<Announcement> = listOf()
)

enum class ClubCategory(val title: String, val color: Color) {
    COMMUNITY_AND_SERVICE("Community and Service", CommunityAndServiceColor),
    AFFINITY_AND_ACTIVISM("Affinity and Activism", AffinityAndActivismColor),
    ACADEMICS("Academics", AcademicsColor),
    ARTS("Arts", ArtsColor),
    HOBBY_AND_SPECIAL_INTERESTS("Hobby and Special Interests", HobbyAndSpecialInterestsColor),
    ATHLETICS("Athletics", AthleticsColor),
    STUDENT_GOVERNMENT_AND_CLASS_CABINET("Student Government and Class Cabinet", StudentGovernmentAndClassCabinetColor),
    PUBLICATIONS("Publications", PublicationsColor)
}

/*
    DocumentReference next to user will refer to the "users"
    collection in firebase
 */
data class Announcement(
    val user: DocumentReference? = null,
    val date: Date = Date(),
    val description: String = "", //May be a typeface object
    val pinned: Boolean = false
)
