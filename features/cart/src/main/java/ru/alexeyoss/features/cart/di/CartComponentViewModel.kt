package ru.alexeyoss.features.cart.di

import androidx.lifecycle.ViewModel

internal class CartComponentViewModel : ViewModel() {

    fun initCartComponent(cartDeps: CartDeps): CartComponent =
        DaggerCartComponent.builder()
            .deps(cartDeps)
            .build()
}