package ru.alexeyoss.foodie.mediators.categories.repositories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.alexeyoss.core.common.Container
import ru.alexeyoss.data.CategoriesDataRepository
import ru.alexeyoss.features.categories.domain.entities.UiCategory
import ru.alexeyoss.features.categories.domain.repositories.CategoriesRepository
import ru.alexeyoss.foodie.mediators.buildRequestFlow
import ru.alexeyoss.foodie.mediators.categories.mappers.CategoryMapper
import javax.inject.Inject

class AdapterCategoriesRepository
@Inject constructor(
    private val categoriesDataRepository: CategoriesDataRepository,
    private val categoryMapper: CategoryMapper
) : CategoriesRepository {

    override suspend fun getCategories(): Flow<Container<List<UiCategory>>> {
        return buildRequestFlow {
            categoriesDataRepository.getCategories()
        }.map { container ->
            container.convert { categoriesListDTO ->
                categoryMapper.mapEntityList(categoriesListDTO.categories)
            }
        }
    }
}