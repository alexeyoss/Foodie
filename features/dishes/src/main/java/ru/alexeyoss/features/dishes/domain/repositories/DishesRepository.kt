package ru.alexeyoss.features.dishes.domain.repositories

import kotlinx.coroutines.flow.Flow
import ru.alexeyoss.features.dishes.domain.models.UiDishDTO
import ru.alexeyoss.foodie.core.common.data.Container

interface DishesRepository {
    suspend fun getDishes(): Flow<Container<List<UiDishDTO>>>
}
