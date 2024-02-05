package com.example.shhsactivities.ui.events

sealed class UiEvent {
    data object PopBackStack: UiEvent()
    data class Navigate(val route: String): UiEvent()
}