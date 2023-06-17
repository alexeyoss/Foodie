package ru.alexeyoss.data

import ru.alexeyoss.network.ResponseStates
import ru.alexeyoss.network.categories.models.CategoryListDTO

interface CategoriesDataRepository {

    suspend fun getCategories(): ResponseStates<CategoryListDTO>
}