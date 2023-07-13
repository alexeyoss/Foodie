package ru.alexeyoss.data.categories.sources

import ru.alexeyoss.network.models.CategoryListDTO
import ru.alexeyoss.network.utils.ResponseStates

interface CategoriesDataSource {

    suspend fun getCategories(): ResponseStates<CategoryListDTO>
}