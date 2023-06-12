package ru.alexeyoss.foodie.redux

import ru.alexeyoss.foodie.data.model.ui.Filter
import ru.alexeyoss.foodie.data.model.ui.UiCategory
import ru.alexeyoss.foodie.data.model.ui.UiDish

data class ApplicationState(
    val UiCategories: List<UiCategory> = emptyList(),
    val UiDishes: List<UiDish> = emptyList(),
    val dishesFilterInfo: DishFilterInfo = DishFilterInfo(),
    val favoriteDishesIds: Set<Int> = emptySet(),
    val inCartDishesIds: Set<Int> = emptySet(),
    val cartQuantitiesMap: Map<Int, Int> = emptyMap(), // productId -> quantity
)

data class DishFilterInfo(
    val filters: Set<Filter> = emptySet(),
    val selectedFilter: Filter? = null
)