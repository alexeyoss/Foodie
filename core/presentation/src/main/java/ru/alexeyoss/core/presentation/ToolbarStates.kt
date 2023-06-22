package ru.alexeyoss.core.presentation

sealed interface ToolbarStates {
    object LocationView : ToolbarStates
    data class CustomTitle(val title: String) : ToolbarStates
}