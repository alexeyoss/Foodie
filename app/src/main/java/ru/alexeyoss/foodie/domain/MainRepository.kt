package ru.alexeyoss.foodie.domain

import ru.alexeyoss.foodie.data.model.ui.UiCategory
import ru.alexeyoss.foodie.data.model.ui.UiDish
import ru.alexeyoss.foodie.data.network.ResponseStates

interface MainRepository {

    suspend fun getCategories(): ResponseStates<List<UiCategory>>

    suspend fun getCategoryDishes(): ResponseStates<List<UiDish>>
}