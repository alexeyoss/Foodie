package ru.alexeyoss.data.categories.sources

import ru.alexeyoss.network.MainApiService
import ru.alexeyoss.network.models.CategoryListDTO
import ru.alexeyoss.network.utils.ResponseStates
import ru.alexeyoss.network.utils.safeApiCall
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