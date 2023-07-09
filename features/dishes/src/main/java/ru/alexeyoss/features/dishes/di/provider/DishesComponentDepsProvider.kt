package ru.alexeyoss.features.dishes.di.provider

import ru.alexeyoss.features.dishes.di.DishesDeps

interface DishesComponentDepsProvider {
    fun getDishesDeps(): DishesDeps
}