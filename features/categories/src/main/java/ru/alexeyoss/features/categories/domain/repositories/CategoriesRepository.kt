package ru.alexeyoss.features.categories.domain.repositories

import kotlinx.coroutines.flow.Flow
import ru.alexeyoss.core.common.Container
import ru.alexeyoss.features.categories.domain.entities.UiCategory

interface CategoriesRepository {

    suspend fun getCategories(): Flow<Container<List<UiCategory>>>
}