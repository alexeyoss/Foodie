package ru.alexeyoss.foodie.coreui.presentation.toolbar

/**
 * Special Toolbar States defined for application needs
 * @see ToolbarState
 * */
sealed interface FoodieToolbarStates : ToolbarState {
    object LocationView : FoodieToolbarStates
    data class CustomTitle(val title: String) : FoodieToolbarStates
}
