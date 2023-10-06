package ru.alexeyoss.foodie.services.data.dishes.sources

import ru.alexeyoss.foodie.services.network.models.responses.DishesListDTO
import ru.alexeyoss.foodie.services.network.utils.ResponseStates

interface DishesDataSource {
    suspend fun getDishes(): ResponseStates<DishesListDTO>
}