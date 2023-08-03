package ru.alexeyoss.data.dishes.sources

import ru.alexeyoss.network.MainApiService
import ru.alexeyoss.network.models.responses.DishesListDTO
import ru.alexeyoss.network.utils.ResponseStates
import ru.alexeyoss.network.utils.safeApiCall
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