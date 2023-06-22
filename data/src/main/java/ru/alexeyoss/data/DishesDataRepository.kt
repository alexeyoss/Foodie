package ru.alexeyoss.data

import ru.alexeyoss.network.ResponseStates
import ru.alexeyoss.network.dishes.models.DishesListDTO

interface DishesDataRepository {

    suspend fun getDishes(): ResponseStates<DishesListDTO>

}