package com.example.shhsactivities.di

import com.example.shhsactivities.data.repositories.ClubRepository
import com.example.shhsactivities.data.repositories.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideClubRepository(): ClubRepository = ClubRepository()

    @Provides
    @Singleton
    fun provideUserRepository(): UserRepository = UserRepository()

}