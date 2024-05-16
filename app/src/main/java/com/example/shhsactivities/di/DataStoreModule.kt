package com.example.shhsactivities.di

import android.content.Context
import androidx.datastore.core.DataStore
import com.example.shhsactivities.UserPreferences
import com.example.shhsactivities.util.userPreferencesStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DataStoreModule {

    @Provides
    @Singleton
    fun providePreferencesDataStore(@ApplicationContext context: Context): DataStore<UserPreferences> =
        context.userPreferencesStore

}