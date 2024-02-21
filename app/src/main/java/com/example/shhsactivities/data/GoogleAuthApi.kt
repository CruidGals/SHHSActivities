package com.example.shhsactivities.data

import android.content.Context
import android.content.Intent
import android.content.IntentSender
import com.example.shhsactivities.R
import com.example.shhsactivities.data.models.UserData
import com.example.shhsactivities.ui.states.GoogleApiState
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.tasks.await
import java.util.concurrent.CancellationException
import javax.inject.Inject

class GoogleAuthApi (
    private val context: Context,
    private val oneTapClient: SignInClient,
    private val auth: FirebaseAuth
) {

    suspend fun signIn(): IntentSender? {
        val result = try {
            oneTapClient.beginSignIn(
                buildSignInRequest()
            ).await()
        } catch (e: Exception) {
            e.printStackTrace()
            if (e is CancellationException) throw e
            null
        }

        return result?.pendingIntent?.intentSender
    }

    suspend fun signOut() {
        try {
            oneTapClient.signOut().await()
            auth.signOut()
        } catch (e: Exception) {
            e.printStackTrace()
            if (e is CancellationException) throw e
        }
    }

    suspend fun signInWithIntent(intent: Intent): GoogleApiState {
        val credential = oneTapClient.getSignInCredentialFromIntent(intent)
        val googleIdToken = credential.googleIdToken
        val googleCredentials = GoogleAuthProvider.getCredential(googleIdToken, null)

        return try {
            val user = auth.signInWithCredential(googleCredentials).await().user
            GoogleApiState.Success(
                userData = user?.run {
                    UserData(
                        uid = uid,
                        email = email,
                        username = displayName,
                        profilePictureUrl = photoUrl?.toString(),
                        phone = phoneNumber
                    )
                }
            )
        } catch (e: Exception) {
            e.printStackTrace()
            if (e is CancellationException) throw e
            GoogleApiState.Error(e)
        }
    }

    fun getSignedInUser(): UserData? = auth.currentUser?.run {
        UserData(
            uid = uid,
            email = email,
            username = displayName,
            profilePictureUrl = photoUrl?.toString(),
            phone = phoneNumber
        )
    }

    private fun buildSignInRequest(): BeginSignInRequest {
        return BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setServerClientId(context.getString(R.string.web_client_id))
                    .setFilterByAuthorizedAccounts(false)
                    .build()
            ).build()
    }

}