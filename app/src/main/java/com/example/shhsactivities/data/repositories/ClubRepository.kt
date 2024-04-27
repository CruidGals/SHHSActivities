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
import java.util.concurrent.CancellationException


class ClubRepository {
    private val db = Firebase.firestore

    suspend fun addClub(document: Club) {
        val club = document.mapWithoutAnnouncements()

        try {
            db.collection("clubs")
                .add(club)
                .addOnSuccessListener { clubRef ->
                    document.announcements.forEach { clubRef.collection("announcements").add(it) }

                    Log.d(TAG, "Club added with ID: ${clubRef.id}")
                }
                .addOnFailureListener {
                    Log.w(TAG, "Error adding club", it)
                }.await()
        } catch (e: Exception) {
            e.printStackTrace()
            if (e is CancellationException) throw e
        }
    }

    suspend fun getAllClubs(): List<DocumentSnapshot> {
        var clubs: List<DocumentSnapshot> = listOf()

        try {
            val result = db.collection("clubs")
                .get()
                .await() // Use await() to wait for the operation to complete in a suspend function

            clubs = result.documents
            Log.d(TAG, "Retrieved ${clubs.size} clubs.")
        } catch (e: Exception) {
            Log.w(TAG, "Error retrieving clubs", e)
        }

        return clubs
    }

    suspend fun getClubById(id: String): DocumentSnapshot? {
        var club: DocumentSnapshot? = null

        try {
            val result = db.collection("clubs")
                .get()
                .await() // Use await() to wait for the operation to complete in a suspend function

            club = result.documents.find { it.id == id }
            if (club != null) {
                Log.d(TAG, "Retrieved club with id $id")
            } else {
                Log.d(TAG, "No club with id $id")
            }
        } catch (e: Exception) {
            Log.w(TAG, "Error retrieving clubs", e)
        }

        return club
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
        var clubs: List<DocumentSnapshot> = listOf()

        try {
            db.collection("clubs")
                .where(filter)
                .get()
                .addOnSuccessListener {
                    try {
                        clubs = it.documents
                        Log.d(TAG, "Clubs (${clubs.size}) with $queryName: $query retrieved.")
                    } catch (e: Exception) {
                        Log.w(TAG, "Clubs with $queryName: $query not found.")
                    }
                }
                .addOnFailureListener {
                    Log.w(TAG, "Error retrieving club", it)
                }.await()
        } catch (e: Exception) {
            e.printStackTrace()
            if (e is CancellationException) throw e
        }

        return clubs
    }

    @Suppress("UNCHECKED_CAST")
    suspend fun toObject(docSnapshot: DocumentSnapshot): Club {
        var announcements: List<Announcement> = listOf()

        docSnapshot.reference.collection("announcements")
            .get()
            .addOnSuccessListener { announcementsSnapshot ->
                announcements = announcementsSnapshot.documents.map {
                    it.toObject(Announcement::class.java)!!
                }
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