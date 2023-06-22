package ru.alexeyoss.features.dishes.domain.models

/**
 * Describe UI data state of DishesFragment
 * */
data class DishListState(
    val dishes: List<UiDishDTO>,
    val filteredDishes: List<UiDishDTO>? = null,
    val filters: Set<UiFilterDTO>
)