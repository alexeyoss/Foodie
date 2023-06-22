package ru.alexeyoss.data.dishes.sources

import ru.alexeyoss.network.ResponseStates
import ru.alexeyoss.network.dishes.models.DishesListDTO

interface DishesDataSource {
    suspend fun getDishes(): ResponseStates<DishesListDTO>
}