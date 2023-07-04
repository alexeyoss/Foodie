package ru.alexeyoss.features.categories.domain

import kotlinx.coroutines.flow.Flow
import ru.alexeyoss.core.common.Container
import ru.alexeyoss.features.categories.domain.entities.UiCategory
import ru.alexeyoss.features.categories.domain.repositories.CategoriesRepository
import javax.inject.Inject

class GetCategoriesUseCase
@Inject constructor(
    private val categoriesRepository: CategoriesRepository
) {

    suspend operator fun invoke(): Flow<Container<List<UiCategory>>> {
        return categoriesRepository.getCategories()
    }
}