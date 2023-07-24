package ru.alexeyoss.data.categories

import ru.alexeyoss.data.categories.sources.CategoriesDataSource
import ru.alexeyoss.network.models.CategoryListDTO
import ru.alexeyoss.network.utils.ResponseStates
import javax.inject.Inject

class RealCategoriesDataRepository
@Inject constructor(
    private val categoriesDataSource: CategoriesDataSource
) : CategoriesDataRepository {
    override suspend fun getCategories(): ResponseStates<CategoryListDTO> {
        return categoriesDataSource.getCategories()
    }
}