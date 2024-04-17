package com.example.shhsactivities.di

import android.content.Context
import com.example.shhsactivities.data.GoogleAuthApi
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object GoogleAuthModule {

    @Provides
    @Singleton
    fun provideContext(@ApplicationContext context: Context): Context = context
    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = Firebase.auth

    @Provides
    @Singleton
    fun provideOneTapClient(@ApplicationContext context: Context): SignInClient =
        Identity.getSignInClient(context)

    @Provides
    @Singleton
    fun provideGoogleAuthApi(@ApplicationContext context: Context, auth: FirebaseAuth, oneTapClient: SignInClient): GoogleAuthApi =
        GoogleAuthApi(
            context,
            oneTapClient,
            auth
        )

}