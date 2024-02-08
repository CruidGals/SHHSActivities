package com.example.shhsactivities.ui.viewmodels.components

/*
* Basic set of classes to handle the ordering of clubs
* within the home screen
 */
sealed class ClubOrder(val direction: OrderDirection) {
    class Title(direction: OrderDirection): ClubOrder(direction)
    class Leadership(direction: OrderDirection): ClubOrder(direction)

    fun copy(direction: OrderDirection): ClubOrder {
        return when(this) {
            is Title -> Title(direction)
            is Leadership -> Leadership(direction)
        }
    }
}

sealed class OrderDirection {
    data object Ascending: OrderDirection()
    data object Descending: OrderDirection()
}