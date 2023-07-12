package ru.alexeyoss.foodie.mediators.dishes.repositories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.alexeyoss.core.common.data.Container
import ru.alexeyoss.data.dishes.DishesDataRepository
import ru.alexeyoss.features.dishes.domain.models.UiDishDTO
import ru.alexeyoss.features.dishes.domain.repositories.DishesRepository
import ru.alexeyoss.foodie.mediators.buildNetworkFlow
import ru.alexeyoss.foodie.mediators.dishes.mappers.DishesMapper
import javax.inject.Inject

class AdapterDishesRepository
@Inject constructor(
    private val dishesDataRepository: DishesDataRepository,
    private val dishesMapper: DishesMapper
) : DishesRepository {

    override suspend fun getDishes(): Flow<Container<List<UiDishDTO>>> {
        return buildNetworkFlow {
            dishesDataRepository.getDishes()
        }.map { container ->
            container.convert { dishesListDTO ->
                dishesMapper.mapEntityList(dishesListDTO.dishes)
            }
        }
    }

}