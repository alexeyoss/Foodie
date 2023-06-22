package ru.alexeyoss.data.dishes

import ru.alexeyoss.data.DishesDataRepository
import ru.alexeyoss.data.dishes.sources.DishesDataSource
import ru.alexeyoss.network.ResponseStates
import ru.alexeyoss.network.dishes.models.DishesListDTO
import javax.inject.Inject

class RealDishesDataRepository
@Inject constructor(
    private val dishesDataSource: DishesDataSource
) : DishesDataRepository {
    override suspend fun getDishes(): ResponseStates<DishesListDTO> {
        return dishesDataSource.getDishes()
    }


}