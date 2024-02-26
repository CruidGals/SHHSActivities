package com.example.shhsactivities.data.repositories

import android.util.Log
import com.example.shhsactivities.data.models.UserData
import com.google.firebase.Firebase
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.tasks.await
import java.util.concurrent.CancellationException
import javax.inject.Singleton

class UserRepository {
    private val db = Firebase.firestore

    suspend fun addUser(document: UserData) {
        try {
            db.collection("users")
                .add(document)
                .addOnSuccessListener {
                    Log.d(TAG, "User added with ID: ${it.id}")
                }
                .addOnFailureListener {
                    Log.w(TAG, "Error adding user", it)
                }.await()
        } catch (e: Exception) {
            e.printStackTrace()
            if (e is CancellationException) throw e
        }
    }

    /*
        Returns the physical "document" of user from firebase
        Use toObject() function to convert back to UserData
     */
    suspend fun getUser(uid: String): DocumentSnapshot? {
        var user: DocumentSnapshot? = null

        try {
            db.collection("users")
                .whereEqualTo("uid", uid)
                .get()
                .addOnSuccessListener {
                    try {
                        user = it.documents[0]
                        Log.d(TAG, "User with id: $uid retrieved.")
                    } catch (e: Exception) {
                        Log.w(TAG, "User with id: $uid not found.")
                    }
                }
                .addOnFailureListener {
                    Log.w(TAG, "Error retrieving user", it)
                }.await()
        } catch (e: Exception) {
            e.printStackTrace()
            if (e is CancellationException) throw e
        }

        return user
    }

    companion object {
        const val TAG = "UserRepository"
    }
}