package ru.alexeyoss.data.dishes

import ru.alexeyoss.data.dishes.sources.DishesDataSource
import ru.alexeyoss.network.models.responses.DishesListDTO
import ru.alexeyoss.network.utils.ResponseStates
import javax.inject.Inject

class RealDishesDataRepository
@Inject constructor(
    private val dishesDataSource: DishesDataSource
) : DishesDataRepository {
    override suspend fun getDishes(): ResponseStates<DishesListDTO> {
        return dishesDataSource.getDishes()
    }

}