package ru.alexeyoss.features.dishes.domain.repositories

import kotlinx.coroutines.flow.Flow
import ru.alexeyoss.core.common.Container
import ru.alexeyoss.features.dishes.domain.models.UiDishDTO


interface DishesRepository {
    suspend fun getDishes(): Flow<Container<List<UiDishDTO>>>
}