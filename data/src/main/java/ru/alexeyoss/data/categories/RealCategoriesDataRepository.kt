package ru.alexeyoss.data.categories

import ru.alexeyoss.data.CategoriesDataRepository
import ru.alexeyoss.data.categories.sources.CategoriesDataSource
import ru.alexeyoss.network.ResponseStates
import ru.alexeyoss.network.categories.models.CategoryListDTO
import javax.inject.Inject

class RealCategoriesDataRepository
@Inject constructor(
    private val categoriesDataSource: CategoriesDataSource
) : CategoriesDataRepository {
    override suspend fun getCategories(): ResponseStates<CategoryListDTO> {
        return categoriesDataSource.getCategories()
    }
}