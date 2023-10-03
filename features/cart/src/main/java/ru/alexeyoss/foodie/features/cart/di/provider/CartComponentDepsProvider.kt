package ru.alexeyoss.foodie.features.cart.di.provider

import ru.alexeyoss.foodie.features.cart.di.CartDeps

interface CartComponentDepsProvider {

    fun getCartDeps(): CartDeps
}