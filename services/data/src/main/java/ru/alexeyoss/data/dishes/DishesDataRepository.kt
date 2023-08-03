package ru.alexeyoss.data.dishes

import ru.alexeyoss.network.models.responses.DishesListDTO
import ru.alexeyoss.network.utils.ResponseStates

interface DishesDataRepository {

    suspend fun getDishes(): ResponseStates<DishesListDTO>

}