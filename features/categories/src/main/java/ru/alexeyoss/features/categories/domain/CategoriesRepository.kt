package ru.alexeyoss.features.categories.domain

import ru.alexeyoss.foodie.data.model.ui.UiCategory
import ru.alexeyoss.foodie.data.network.ResponseStates

interface CategoriesRepository {

    suspend fun getCategories(): ResponseStates<List<UiCategory>>
}