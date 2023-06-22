package ru.alexeyoss.data.dishes.sources

import ru.alexeyoss.network.MainApiService
import ru.alexeyoss.network.ResponseStates
import ru.alexeyoss.network.dishes.models.DishesListDTO
import ru.alexeyoss.network.safeApiCall
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