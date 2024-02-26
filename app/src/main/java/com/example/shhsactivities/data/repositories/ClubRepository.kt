package com.example.shhsactivities.data.repositories

import android.util.Log
import com.example.shhsactivities.data.models.Club
import com.google.firebase.Firebase
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await
import java.util.concurrent.CancellationException


class ClubRepository {
    private val db = Firebase.firestore

    suspend fun addClub(document: Club) {
        try {
            db.collection("clubs")
                .add(document)
                .addOnSuccessListener {
                    Log.d(TAG, "Club added with ID: ${it.id}")
                }
                .addOnFailureListener {
                    Log.w(TAG, "Error adding club", it)
                }.await()
        } catch (e: Exception) {
            e.printStackTrace()
            if (e is CancellationException) throw e
        }
    }

    suspend fun getClubByName(name: String): DocumentSnapshot? {
        return getClubsByQuery(Filter.equalTo("name", name), "name", name)[0]
    }

    /*
        Member is a documentReference from users collection
     */
    suspend fun getClubsByMember(member: DocumentReference): List<DocumentSnapshot?> {
        return getClubsByQuery(Filter.arrayContains("members", member), "member", member.id)
    }

    /*
        Returns the physical "document" of club from firebase
        Use toObject() function to convert back to Club
        Parameters queryName and query or used for logcat
     */
    private suspend fun getClubsByQuery(filter: Filter, queryName: String = "", query: Any = ""): List<DocumentSnapshot?> {
        var clubs: List<DocumentSnapshot?> = listOf()

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

    companion object {
        const val TAG = "UserRepository"
    }
}