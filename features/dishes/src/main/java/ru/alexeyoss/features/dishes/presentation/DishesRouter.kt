package ru.alexeyoss.features.dishes.presentation

import ru.alexeyoss.features.dishes.domain.models.UiDishDTO

interface DishesRouter {

    fun launchDishDetailsDialog(uiDish: UiDishDTO)
}