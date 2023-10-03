package ru.alexeyoss.core_ui.presentation.toolbar

/**
 * Screen interface for holding certain [FoodieToolbarStates]
 * @see FoodieToolbarStates
 * */
interface ToolbarStateOwner {
    fun getToolbarState(): FoodieToolbarStates
}
