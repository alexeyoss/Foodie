package ru.alexeyoss.foodie.features.categories.di

import androidx.lifecycle.ViewModel

internal class CategoriesComponentViewModel : ViewModel() {

    fun initCategoriesComponent(categoriesDeps: CategoriesDeps): CategoriesComponent =
        DaggerCategoriesComponent.builder()
            .deps(categoriesDeps)
            .build()
}