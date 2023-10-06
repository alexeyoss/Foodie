package ru.alexeyoss.foodie.services.data.categories.sources

import ru.alexeyoss.foodie.services.network.models.responses.CategoryListDTO
import ru.alexeyoss.foodie.services.network.utils.ResponseStates

interface CategoriesDataSource {

    suspend fun getCategories(): ResponseStates<CategoryListDTO>
}