package ru.alexeyoss.features.cart.di.provider

import ru.alexeyoss.features.cart.di.CartDeps

interface CartComponentDepsProvider {

    fun getCartDeps(): CartDeps
}