package ru.alexeyoss.foodie.services.data.categories

import ru.alexeyoss.foodie.services.data.categories.sources.CategoriesDataSource
import ru.alexeyoss.foodie.services.network.models.responses.CategoryListDTO
import ru.alexeyoss.foodie.services.network.utils.ResponseStates
import javax.inject.Inject

class RealCategoriesDataRepository
@Inject constructor(
    private val categoriesDataSource: CategoriesDataSource
) : CategoriesDataRepository {
    override suspend fun getCategories(): ResponseStates<CategoryListDTO> {
        return categoriesDataSource.getCategories()
    }
}