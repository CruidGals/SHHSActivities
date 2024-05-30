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
            db.collection("users").add(document).await()
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
        try {
            val userSnapshot = db.collection("users").whereEqualTo("uid", uid).get().await()
            return userSnapshot.documents[0]
        } catch (e: Exception) {
            e.printStackTrace()
            if (e is CancellationException) throw e
        }

        return null
    }

    companion object {
        const val TAG = "UserRepository"
    }
}