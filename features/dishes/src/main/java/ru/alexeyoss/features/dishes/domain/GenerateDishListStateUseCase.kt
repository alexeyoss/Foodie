package ru.alexeyoss.features.dishes.domain

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import ru.alexeyoss.core.common.di.modules.CoroutinesModule
import ru.alexeyoss.features.dishes.domain.models.DishListState
import ru.alexeyoss.features.dishes.domain.models.UiDishDTO
import ru.alexeyoss.features.dishes.domain.models.UiFilterDTO
import javax.inject.Inject

class GenerateDishListStateUseCase
@Inject constructor(
    @CoroutinesModule.DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(dishList: List<UiDishDTO>): DishListState {
        // Extract unique tags from Api response [UiDishDTO] Build common [Set] of [UiFilterDTO]s
        val filtersSet = withContext(defaultDispatcher) {
            dishList
                .flatMap { uiDish -> uiDish.tegs }
                .distinct()
                .map { tag -> UiFilterDTO(value = tag, displayText = tag, isChecked = false) }
        }.toSet()
        return DishListState(
            dishes = dishList,
            filteredDishes = dishList,
            filters = filtersSet
        )

    }
}