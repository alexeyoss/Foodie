package ru.alexeyoss.features.dishes.di

import androidx.lifecycle.ViewModel

internal class DishesComponentViewModel : ViewModel() {

    fun initDishesComponent(dishesDeps: DishesDeps): DishesComponent {
        return DaggerDishesComponent.builder()
            .deps(dishesDeps)
            .build()
    }

}