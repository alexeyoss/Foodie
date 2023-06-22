package ru.alexeyoss.features.categories.presentation

interface CategoryRouter {

    /**
     * Go to Dishes screen feature
     * */
    fun launchDishesScreen(categoryName: String)

    /**
     * Go back to the previous screen.
     */
    fun goBack()
}