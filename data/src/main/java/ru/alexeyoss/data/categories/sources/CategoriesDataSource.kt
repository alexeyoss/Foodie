package ru.alexeyoss.data.categories.sources

import ru.alexeyoss.network.ResponseStates
import ru.alexeyoss.network.categories.models.CategoryListDTO

interface CategoriesDataSource {

    suspend fun getCategories(): ResponseStates<CategoryListDTO>
}