package ru.alexeyoss.core_ui.presentation.toolbar

import ru.alexeyoss.foodie.coreui.presentation.toolbar.FoodieToolbarStates

/**
 * Screen interface for holding certain [FoodieToolbarStates]
 * @see FoodieToolbarStates
 * */
interface ToolbarStateOwner {
    fun getToolbarState(): FoodieToolbarStates
}
