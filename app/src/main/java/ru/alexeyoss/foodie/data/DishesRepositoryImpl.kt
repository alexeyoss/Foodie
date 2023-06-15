package ru.alexeyoss.foodie.data

//class DishesRepositoryImpl
//@Inject constructor(
//    private val apiService: ApiService,
//    private val dishMapper: DishMapper
//) : ru.alexeyoss.features.dishes.domain.DishesRepository {
//
//    override suspend fun getCategoryDishes(): ResponseStates<List<UiDish>> {
//        return safeCall {
//            apiService.getCategoryDishes().let { dishesList ->
//                dishMapper.mapEntityList(dishesList.dishes)
//            }
//        }
//    }
//}