package ru.alexeyoss.data.categories.sources

import ru.alexeyoss.network.models.responses.CategoryListDTO
import ru.alexeyoss.network.utils.ResponseStates

interface CategoriesDataSource {

    suspend fun getCategories(): ResponseStates<CategoryListDTO>
}