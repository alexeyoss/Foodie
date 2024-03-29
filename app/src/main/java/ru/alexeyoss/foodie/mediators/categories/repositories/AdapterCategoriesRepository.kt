package ru.alexeyoss.foodie.mediators.categories.repositories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.alexeyoss.foodie.core.common.data.Container
import ru.alexeyoss.foodie.services.data.categories.CategoriesDataRepository
import ru.alexeyoss.foodie.features.categories.domain.entities.UiCategory
import ru.alexeyoss.foodie.features.categories.domain.repositories.CategoriesRepository
import ru.alexeyoss.foodie.mediators.buildNetworkFlow

import ru.alexeyoss.foodie.mediators.categories.mappers.CategoryMapper
import javax.inject.Inject

class
AdapterCategoriesRepository
@Inject constructor(
    private val categoriesDataRepository: CategoriesDataRepository,
    private val categoryMapper: CategoryMapper
) : CategoriesRepository {

    override suspend fun getCategories(): Flow<Container<List<UiCategory>>> {
        return buildNetworkFlow {
            categoriesDataRepository.getCategories()
        }.map { container ->
            container.convert { categoriesListDTO ->
                categoryMapper.mapEntityList(categoriesListDTO.categories)
            }
        }
    }
}