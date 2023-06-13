package ru.alexeyoss.foodie.data

import ru.alexeyoss.foodie.data.model.mappers.CategoryMapper
import ru.alexeyoss.foodie.data.model.mappers.DishMapper
import ru.alexeyoss.foodie.data.model.ui.UiCategory
import ru.alexeyoss.foodie.data.model.ui.UiDish
import ru.alexeyoss.foodie.data.network.ApiService
import ru.alexeyoss.foodie.data.network.ResponseStates
import ru.alexeyoss.foodie.data.network.safeCall
import ru.alexeyoss.foodie.domain.MainRepository
import javax.inject.Inject

class MainRepositoryImpl
@Inject constructor(
    private val apiService: ApiService, private val categoryMapper: CategoryMapper, private val dishMapper: DishMapper
) : MainRepository {

    override suspend fun getCategories(): ResponseStates<List<UiCategory>> {
        return safeCall {
            apiService.getCategories().let { categoryList ->
                categoryMapper.mapEntityList(categoryList.categories)
            }
        }
    }

    override suspend fun getCategoryDishes(): ResponseStates<List<UiDish>> {
        return safeCall {
            apiService.getCategoryDishes().let { dishesList ->
                dishMapper.mapEntityList(dishesList.dishes)
            }
        }
    }


}