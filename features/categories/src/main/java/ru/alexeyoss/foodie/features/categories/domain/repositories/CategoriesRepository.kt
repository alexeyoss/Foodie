package ru.alexeyoss.foodie.features.categories.domain.repositories

import kotlinx.coroutines.flow.Flow
import ru.alexeyoss.foodie.core.common.data.Container
import ru.alexeyoss.foodie.features.categories.domain.entities.UiCategory

interface CategoriesRepository {

    suspend fun getCategories(): Flow<Container<List<UiCategory>>>
}
