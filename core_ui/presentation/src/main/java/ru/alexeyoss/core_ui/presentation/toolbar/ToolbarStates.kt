package ru.alexeyoss.core_ui.presentation.toolbar

/**
 * Special Toolbar States defined for application needs
 * @see BaseToolbarState
 * */
sealed interface ToolbarStates : BaseToolbarState {
    object LocationView : ToolbarStates
    data class CustomTitle(val title: String) : ToolbarStates
}

