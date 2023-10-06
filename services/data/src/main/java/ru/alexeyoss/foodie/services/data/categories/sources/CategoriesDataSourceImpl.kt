package ru.alexeyoss.foodie.services.data.categories.sources

import ru.alexeyoss.foodie.services.network.MainApiService
import ru.alexeyoss.foodie.services.network.models.responses.CategoryListDTO
import ru.alexeyoss.foodie.services.network.utils.ResponseStates
import ru.alexeyoss.foodie.services.network.utils.safeApiCall
import javax.inject.Inject

class CategoriesDataSourceImpl
@Inject
constructor(
    private val apiService: MainApiService
) : CategoriesDataSource {
    override suspend fun getCategories(): ResponseStates<CategoryListDTO> {
        return safeApiCall {
            apiService.getCategories()
        }
    }
}