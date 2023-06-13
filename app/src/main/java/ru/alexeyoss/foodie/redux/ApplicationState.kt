package ru.alexeyoss.foodie.redux

import ru.alexeyoss.foodie.data.model.ui.UiCategory
import ru.alexeyoss.foodie.data.model.ui.UiDish
import ru.alexeyoss.foodie.data.model.ui.UiFilter

data class ApplicationState(
    val UiCategories: List<UiCategory> = emptyList(),
    val UiDishes: List<UiDish> = emptyList(),
//    val dishesFilterInfo: DishFilterInfo = DishFilterInfo(),
//    val favoriteDishesIds: Set<Int> = emptySet(),
//    val inCartDishesIds: Set<Int> = emptySet(),
//    val cartQuantitiesMap: Map<Int, Int> = emptyMap(), // productId -> quantity
)

data class DishFilterInfo(
    val filters: Set<UiFilter> = emptySet(),
    val selectedFilter: UiFilter? = null
)