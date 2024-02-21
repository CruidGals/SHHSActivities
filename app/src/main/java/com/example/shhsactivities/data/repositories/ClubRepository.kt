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

    /*
        Returns the physical "document" of club from firebase
        Use toObject() function to convert back to Club
     */
    suspend fun getClubByName(name: String): DocumentSnapshot? {
        var club: DocumentSnapshot? = null

        try {
            db.collection("clubs")
                .whereEqualTo("name", name)
                .get()
                .addOnSuccessListener {
                    try {
                        club = it.documents[0]
                        Log.d(TAG, "Club with name: $name retrieved.")
                    } catch (e: Exception) {
                        Log.w(TAG, "Club with name: $name not found.")
                    }
                }
                .addOnFailureListener {
                    Log.w(TAG, "Error retrieving club", it)
                }.await()
        } catch (e: Exception) {
            e.printStackTrace()
            if (e is CancellationException) throw e
        }

        return club
    }

    companion object {
        const val TAG = "UserRepository"
    }
}