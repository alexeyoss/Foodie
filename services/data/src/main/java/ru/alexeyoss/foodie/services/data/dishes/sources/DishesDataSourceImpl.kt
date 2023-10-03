package ru.alexeyoss.foodie.services.data.dishes.sources

import ru.alexeyoss.foodie.services.network.MainApiService
import ru.alexeyoss.foodie.services.network.models.responses.DishesListDTO
import ru.alexeyoss.foodie.services.network.utils.ResponseStates
import ru.alexeyoss.foodie.services.network.utils.safeApiCall
import javax.inject.Inject

class DishesDataSourceImpl
@Inject constructor(
    private val apiService: MainApiService
) : DishesDataSource {

    override suspend fun getDishes(): ResponseStates<DishesListDTO> {
        return safeApiCall {
            apiService.getDishes()
        }
    }
}