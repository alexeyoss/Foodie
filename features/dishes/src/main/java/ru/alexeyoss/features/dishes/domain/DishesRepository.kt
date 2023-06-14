package ru.alexeyoss.features.dishes.domain

import ru.alexeyoss.features.dishes.domain.entities.UiDish
import ru.alexeyoss.foodie.data.network.ResponseStates

interface DishesRepository {
    suspend fun getCategoryDishes(): ResponseStates<List<UiDish>>
}