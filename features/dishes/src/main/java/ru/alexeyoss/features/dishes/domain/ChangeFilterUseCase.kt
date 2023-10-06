package ru.alexeyoss.features.dishes.domain

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import ru.alexeyoss.features.dishes.domain.models.DishListState
import ru.alexeyoss.features.dishes.domain.models.UiFilterDTO
import ru.alexeyoss.foodie.core.common.di.modules.CoroutinesModule
import javax.inject.Inject

/**
 * Use case for filtering [DishListState.dishes]
 * */
class ChangeFilterUseCase
@Inject constructor(
    @CoroutinesModule.DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher
) {

    suspend operator fun invoke(
        lastDishListState: DishListState,
        newFilterState: UiFilterDTO
    ): DishListState {
        return withContext(defaultDispatcher) {
            // Find the certain filter, remove it and add new one with isChecked = value
            val newFilterSet = lastDishListState.filters.map { oldFilterState ->
                if (oldFilterState.value == newFilterState.value) {
                    oldFilterState.copy(isChecked = !newFilterState.isChecked)
                } else {
                    oldFilterState
                }
            }.toSet()

            // Extract all values where [isChecked == true] from newFilterList
            val activeFiltersValue = newFilterSet.filter { uiFilterDTO -> uiFilterDTO.isChecked }
                .map { uiFilterDTO -> uiFilterDTO.value }

            // Filter dishesList by active tags from [activeFiltersValue]
            val filteredDishes = lastDishListState.dishes.filter { dish ->
                dish.tegs.any { tag -> tag in activeFiltersValue }
            }

            if (activeFiltersValue.isEmpty()) {
                return@withContext lastDishListState.copy(
                    filteredDishes = lastDishListState.dishes,
                    filters = newFilterSet
                )
            } else {
                return@withContext lastDishListState.copy(
                    filteredDishes = filteredDishes,
                    filters = newFilterSet
                )
            }
        }
    }
}
