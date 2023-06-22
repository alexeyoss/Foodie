package ru.alexeyoss.features.dishes.domain

import kotlinx.coroutines.flow.Flow
import ru.alexeyoss.core.common.Container
import ru.alexeyoss.features.dishes.domain.models.UiDishDTO
import ru.alexeyoss.features.dishes.domain.repositories.DishesRepository
import javax.inject.Inject

class GetDishesUseCase
@Inject constructor(
    private val dishesRepository: DishesRepository
) {
    suspend operator fun invoke(): Flow<Container<List<UiDishDTO>>> {
        return dishesRepository.getDishes()
    }
}