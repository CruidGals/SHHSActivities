package com.example.shhsactivities.data.repositories

import android.util.Log
import com.example.shhsactivities.data.models.Announcement
import com.example.shhsactivities.data.models.Club
import com.example.shhsactivities.data.models.ClubCategory
import com.google.firebase.Firebase
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.tasks.await
import java.util.Date
import java.util.concurrent.CancellationException


class ClubRepository {
    private val db = Firebase.firestore

    suspend fun addClub(document: Club) {
        val club = document.mapWithoutAnnouncements()

        try {
            val clubRef = db.collection("clubs").add(club).await()
            document.announcements.forEach { clubRef.collection("announcements").add(it) }
        } catch (e: Exception) {
            e.printStackTrace()
            if (e is CancellationException) throw e
        }
    }

    suspend fun getAllClubs(): List<DocumentSnapshot> {

        try {
            val result = db.collection("clubs").get().await()
            return result.documents
        } catch (e: Exception) {
            Log.w(TAG, "Error retrieving clubs", e)
        }

        return listOf()
    }

    suspend fun getClubById(id: String): DocumentSnapshot? {
        try {
            val result = db.collection("clubs").get().await()
            return result.documents.find { it.id == id }
        } catch (e: Exception) {
            Log.w(TAG, "Error retrieving clubs", e)
        }

        return null
    }

    suspend fun getClubByName(name: String): DocumentSnapshot {
        return getClubsByQuery(Filter.equalTo("name", name), "name", name)[0]
    }

    /*
        Member is a documentReference from users collection
     */
    suspend fun getClubsByMember(member: DocumentReference): List<DocumentSnapshot> {
        return getClubsByQuery(Filter.arrayContains("members", member), "member", member.id)
    }

    /*
        Returns the physical "document" of club from firebase
        Use toObject() function to convert back to Club
        Parameters queryName and query or used for logcat
     */
    private suspend fun getClubsByQuery(filter: Filter, queryName: String = "", query: Any = ""): List<DocumentSnapshot> {

        try {
            val clubsSnapshot = db.collection("clubs").where(filter).get().await()
            return clubsSnapshot.documents
        } catch (e: Exception) {
            e.printStackTrace()
            if (e is CancellationException) throw e
        }

        return listOf()
    }

    @Suppress("UNCHECKED_CAST")
    suspend fun toObject(docSnapshot: DocumentSnapshot): Club {
        val announcementsSnapshot = docSnapshot.reference.collection("announcements").get().await()
        val announcements = announcementsSnapshot.documents.mapNotNull {
            it.toObject(Announcement::class.java)
        }

        return Club(
            name = docSnapshot.getString("name") ?: "",
            imageUrl = docSnapshot.getString("imageUrl") ?: "",
            room = docSnapshot.getString("room") ?: "",
            meetingFrequency = docSnapshot.getString("meetingFrequency") ?: "",
            description = docSnapshot.getString("description") ?: "",
            category = try {
                ClubCategory.valueOf(docSnapshot.getString("category") ?: "")
            } catch (e: Exception) {
                ClubCategory.PUBLICATIONS
            },
            administrators = docSnapshot.get("administrators") as? List<DocumentReference> ?: listOf(),
            members = docSnapshot.get("members") as? List<DocumentReference> ?: listOf(),
            announcements = announcements
        )
    }

    companion object {
        const val TAG = "ClubRepository"
    }
}