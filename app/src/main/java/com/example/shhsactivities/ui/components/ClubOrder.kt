package com.example.shhsactivities.ui.components

import com.example.shhsactivities.data.GoogleAuthApi
import com.example.shhsactivities.data.models.Club
import com.example.shhsactivities.data.models.ClubCategory
import com.example.shhsactivities.data.models.UserData
import com.example.shhsactivities.data.repositories.UserRepository
import com.google.firebase.firestore.DocumentReference
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.runBlocking
import javax.inject.Singleton

class ClubOrder(
    private val userRepository: UserRepository,
    private val googleAuthApi: GoogleAuthApi
) {
    private val signedInUser = runBlocking {
        coroutineScope {
            val deferredUser = async { userRepository.getUser(googleAuthApi.getSignedInUser()?.uid ?: "1aBcDeFg") }
            val user = deferredUser.await()
            user?.reference
        }
    }

    fun getClubs(
        clubs: List<Club>,
        order: OrderType
    ): List<Club> {
        val filteredClubs = if(order.categories.isEmpty()) clubs else clubs.filter { it.category in order.categories }

        return when (order.direction) {
            is OrderDirection.Ascending -> {
                when (order) {
                    is OrderType.Title -> filteredClubs.sortedBy { it.name.lowercase() }
                    is OrderType.Leadership -> {
                        filteredClubs.filter { signedInUser in it.administrators }
                            .sortedBy { it.name.lowercase() } +
                                filteredClubs.filter { signedInUser !in it.administrators }
                                    .sortedBy { it.name.lowercase() }
                    }
                }
            }
            is OrderDirection.Descending -> {
                when (order) {
                    is OrderType.Title -> filteredClubs.sortedByDescending { it.name.lowercase() }
                    is OrderType.Leadership -> {
                        filteredClubs.filter { signedInUser !in it.administrators }
                            .sortedBy { it.name.lowercase() } +
                                filteredClubs.filter { signedInUser in it.administrators }
                                    .sortedBy { it.name.lowercase() }
                    }
                }
            }
        }
    }
}

/*
* Basic set of classes to handle the ordering of clubs
* within the home screen
 */
sealed class OrderType(val direction: OrderDirection, val categories: List<ClubCategory>) {
    class Title(direction: OrderDirection, categories: List<ClubCategory> = listOf()): OrderType(direction, categories)
    class Leadership(direction: OrderDirection, categories: List<ClubCategory> = listOf()): OrderType(direction, categories)

    fun copy(direction: OrderDirection, categories: List<ClubCategory>): OrderType {
        return when(this) {
            is Title -> Title(direction, categories)
            is Leadership -> Leadership(direction, categories)
        }
    }
}

sealed class OrderDirection {
    data object Ascending: OrderDirection() {
        override fun toString(): String = "▲"
    }
    data object Descending: OrderDirection() {
        override fun toString(): String = "▼"
    }
}