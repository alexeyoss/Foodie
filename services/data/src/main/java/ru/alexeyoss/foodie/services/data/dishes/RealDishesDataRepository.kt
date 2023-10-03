package ru.alexeyoss.foodie.services.data.dishes

import ru.alexeyoss.foodie.services.data.dishes.sources.DishesDataSource
import ru.alexeyoss.foodie.services.network.models.responses.DishesListDTO
import ru.alexeyoss.foodie.services.network.utils.ResponseStates
import javax.inject.Inject

class RealDishesDataRepository
@Inject constructor(
    private val dishesDataSource: DishesDataSource
) : DishesDataRepository {
    override suspend fun getDishes(): ResponseStates<DishesListDTO> {
        return dishesDataSource.getDishes()
    }

}