package ru.alexeyoss.data.dishes.sources

import ru.alexeyoss.network.models.responses.DishesListDTO
import ru.alexeyoss.network.utils.ResponseStates

interface DishesDataSource {
    suspend fun getDishes(): ResponseStates<DishesListDTO>
}