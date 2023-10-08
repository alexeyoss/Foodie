package ru.alexeyoss.foodie.di

import ru.alexeyoss.features.dishes.di.DishesDeps
import ru.alexeyoss.features.dishes.di.provider.DishesComponentDepsProvider
import ru.alexeyoss.foodie.features.cart.di.CartDeps
import ru.alexeyoss.foodie.features.cart.di.provider.CartComponentDepsProvider
import ru.alexeyoss.foodie.features.categories.di.CategoriesDeps
import ru.alexeyoss.foodie.features.categories.di.provider.CategoriesComponentDepsProvider

interface AppComponentDepsProvider :
    CategoriesComponentDepsProvider,
    DishesComponentDepsProvider,
    CartComponentDepsProvider {

    val appComponent: AppComponent
    override fun getCategoryDeps(): CategoriesDeps = appComponent

    override fun getCartDeps(): CartDeps = appComponent

    override fun getDishesDeps(): DishesDeps = appComponent
}
