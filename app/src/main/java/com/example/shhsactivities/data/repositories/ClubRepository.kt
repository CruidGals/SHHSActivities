package com.example.shhsactivities.data.repositories

import android.util.Log
import com.example.shhsactivities.data.models.Club
import com.example.shhsactivities.data.models.UserData
import com.google.firebase.Firebase
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.tasks.await
import java.util.concurrent.CancellationException

class ClubRepository {
    private val db = Firebase.firestore

    fun addClub(document: Club) {
        db.collection("clubs")
            .add(document)
            .addOnSuccessListener {
                Log.d(TAG, "Club added with ID: ${it.id}")
            }
            .addOnFailureListener {
                Log.w(TAG, "Error adding club", it)
            }


    }

    suspend fun getClubByName(name: String): DocumentSnapshot? {
        return getClubsByQuery("name", name)[0]
    }

    /*
        Returns the physical "document" of club from firebase
        Use toObject() function to convert back to Club
     */
    private suspend fun getClubsByQuery(queryName: String, query: Any): List<DocumentSnapshot?> {
        var clubs: List<DocumentSnapshot?> = listOf()

        try {
            db.collection("clubs")
                .whereEqualTo(queryName, query)
                .get()
                .addOnSuccessListener {
                    try {
                        clubs = it.documents
                        Log.d(TAG, "Club with $queryName: $query retrieved.")
                    } catch (e: Exception) {
                        Log.w(TAG, "Club with $queryName: $query not found.")
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