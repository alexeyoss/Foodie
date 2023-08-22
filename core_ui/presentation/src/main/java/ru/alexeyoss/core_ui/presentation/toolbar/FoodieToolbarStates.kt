package ru.alexeyoss.core_ui.presentation.toolbar

/**
 * Special Toolbar States defined for application needs
 * @see BaseToolbarState
 * */
sealed interface FoodieToolbarStates : BaseToolbarState {
    object LocationView : FoodieToolbarStates
    data class CustomTitle(val title: String) : FoodieToolbarStates
}

