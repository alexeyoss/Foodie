package ru.alexeyoss.foodie.features.categories.domain

import kotlinx.coroutines.flow.Flow
import ru.alexeyoss.foodie.core.common.data.Container
import ru.alexeyoss.foodie.features.categories.domain.entities.UiCategory
import ru.alexeyoss.foodie.features.categories.domain.repositories.CategoriesRepository
import javax.inject.Inject

class GetCategoriesUseCase
@Inject constructor(
    private val categoriesRepository: CategoriesRepository
) {

    suspend operator fun invoke(): Flow<Container<List<UiCategory>>> {
        return categoriesRepository.getCategories()
    }
}
