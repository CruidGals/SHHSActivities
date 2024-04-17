package com.example.shhsactivities.ui.components

import com.example.shhsactivities.data.models.Club
import com.example.shhsactivities.data.models.ClubCategory
import com.google.firebase.firestore.DocumentReference

class ClubOrder {
    fun getClubs(
        user: DocumentReference,
        clubs: List<Club>,
        orderType: OrderType
    ): List<Club> {
        return when (orderType.direction) {
            is OrderDirection.Ascending -> {
                when (orderType) {
                    is OrderType.Title -> clubs.sortedBy { it.name.lowercase() }
                    is OrderType.Leadership -> clubs.sortedBy { it.}
                }
            }
            is OrderDirection.Descending -> {
                when (orderType) {
                    is OrderType.Title -> recipes.sortedByDescending { it.title.lowercase() }
                    is OrderType.Difficulty -> recipes.sortedByDescending { it.difficulty.length }
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