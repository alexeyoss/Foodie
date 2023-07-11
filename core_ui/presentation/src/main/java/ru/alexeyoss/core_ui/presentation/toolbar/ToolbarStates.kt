package ru.alexeyoss.core_ui.presentation.toolbar

sealed interface ToolbarStates {
    object LocationView : ToolbarStates
    data class CustomTitle(val title: String) : ToolbarStates
}