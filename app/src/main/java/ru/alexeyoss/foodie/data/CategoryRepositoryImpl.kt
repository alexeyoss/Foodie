package ru.alexeyoss.foodie.data

import ru.alexeyoss.features.categories.domain.CategoriesRepository
import ru.alexeyoss.foodie.data.model.mappers.CategoryMapper
import ru.alexeyoss.features.categories.domain.entities.UiCategory
import ru.alexeyoss.foodie.data.network.ApiService
import ru.alexeyoss.foodie.data.network.ResponseStates
import ru.alexeyoss.foodie.data.network.safeCall
import javax.inject.Inject

class CategoryRepositoryImpl
@Inject constructor(
    private val apiService: ApiService,
    private val categoryMapper: CategoryMapper,
) : CategoriesRepository {

    override suspend fun getCategories(): ResponseStates<List<UiCategory>> {
        return safeCall {
            apiService.getCategories().let { categoryList ->
                categoryMapper.mapEntityList(categoryList.categories)
            }
        }
    }


}