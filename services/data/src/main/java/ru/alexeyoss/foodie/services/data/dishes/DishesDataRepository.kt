package ru.alexeyoss.foodie.services.data.dishes

import ru.alexeyoss.foodie.services.network.models.responses.DishesListDTO
import ru.alexeyoss.foodie.services.network.utils.ResponseStates

interface DishesDataRepository {

    suspend fun getDishes(): ResponseStates<DishesListDTO>

}