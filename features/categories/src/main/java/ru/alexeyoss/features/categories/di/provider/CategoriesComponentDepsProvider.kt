package ru.alexeyoss.features.categories.di.provider

import ru.alexeyoss.features.categories.di.CategoriesDeps

interface CategoriesComponentDepsProvider {
    fun getCategoriesDeps(): CategoriesDeps
}