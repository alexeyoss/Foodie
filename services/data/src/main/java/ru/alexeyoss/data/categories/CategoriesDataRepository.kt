package ru.alexeyoss.data.categories

import ru.alexeyoss.network.models.responses.CategoryListDTO
import ru.alexeyoss.network.utils.ResponseStates

interface CategoriesDataRepository {

    suspend fun getCategories(): ResponseStates<CategoryListDTO>
}