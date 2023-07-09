package ru.alexeyoss.features.dishes.presentation

import ru.alexeyoss.features.dishes.domain.models.UiDishDTO

interface DishesRouter {
    // TODO implement Dialog fragments support in Cicerone https://github.com/terrakok/Cicerone/discussions/136
    fun launchDishDetailsDialog(uiDish: UiDishDTO)

    /**
     * Go back to the previous screen.
     */
    fun goBack()
}