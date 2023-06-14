package ru.alexeyoss.features.dishes.domain

import ru.alexeyoss.foodie.data.model.ui.UiDish
import ru.alexeyoss.foodie.data.network.ResponseStates

interface DishesRepository {
    suspend fun getCategoryDishes(): ResponseStates<List<UiDish>>
}