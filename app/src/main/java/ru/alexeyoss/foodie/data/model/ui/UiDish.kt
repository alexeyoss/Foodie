package ru.alexeyoss.foodie.data.model.ui

import ru.alexeyoss.foodie.data.model.network.DishDTO
data class UiDish(
    val dish: DishDTO,
    val isFavorite: Boolean = false,
    val isInCart: Boolean = false
)