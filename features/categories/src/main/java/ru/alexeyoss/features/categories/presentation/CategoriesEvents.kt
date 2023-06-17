package ru.alexeyoss.features.categories.presentation

sealed interface CategoriesEvents {
    /**
     * Get categories from data source
     * */
    object GetCategories : CategoriesEvents
}