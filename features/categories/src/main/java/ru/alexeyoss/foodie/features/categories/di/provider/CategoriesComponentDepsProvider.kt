package ru.alexeyoss.foodie.features.categories.di.provider

import ru.alexeyoss.foodie.features.categories.di.CategoriesDeps

interface CategoriesComponentDepsProvider {
    fun getCategoryDeps(): CategoriesDeps
}