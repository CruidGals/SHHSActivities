package com.example.shhsactivities.data.repositories

import androidx.datastore.core.DataStore
import com.example.shhsactivities.UserData
import com.example.shhsactivities.UserPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserPreferencesRepository @Inject constructor(
    private val userPreferencesStore: DataStore<UserPreferences>
){
    val userPreferencesFlow: Flow<UserPreferences> = userPreferencesStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(UserPreferences.getDefaultInstance())
            } else {
                throw exception
            }
        }

    suspend fun updateUserData(userData: UserData) {
        userPreferencesStore.updateData { currentPreferences ->
            currentPreferences.toBuilder().setUser(userData).build()
        }
    }

    suspend fun getUserData() = userPreferencesFlow.first().user
}