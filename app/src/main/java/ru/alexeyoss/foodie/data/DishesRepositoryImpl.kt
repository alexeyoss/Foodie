package ru.alexeyoss.foodie.data

import ru.alexeyoss.foodie.data.model.mappers.DishMapper
import ru.alexeyoss.foodie.data.model.ui.UiDish
import ru.alexeyoss.foodie.data.network.ApiService
import ru.alexeyoss.foodie.data.network.ResponseStates
import ru.alexeyoss.foodie.data.network.safeCall
import ru.alexeyoss.features.dishes.domain.DishesRepository
import javax.inject.Inject

class DishesRepositoryImpl
@Inject constructor(
    private val apiService: ApiService,
    private val dishMapper: DishMapper
): ru.alexeyoss.features.dishes.domain.DishesRepository {

    override suspend fun getCategoryDishes(): ResponseStates<List<UiDish>> {
        return safeCall {
            apiService.getCategoryDishes().let { dishesList ->
                dishMapper.mapEntityList(dishesList.dishes)
            }
        }
    }
}