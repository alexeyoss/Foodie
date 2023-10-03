package ru.alexeyoss.foodie.services.data.categories

import ru.alexeyoss.foodie.services.network.models.responses.CategoryListDTO
import ru.alexeyoss.foodie.services.network.utils.ResponseStates

interface CategoriesDataRepository {

    suspend fun getCategories(): ResponseStates<CategoryListDTO>
}