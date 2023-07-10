package ru.alexeyoss.foodie.mediators.cart.di

import dagger.Binds
import dagger.Module
import ru.alexeyoss.features.cart.domain.repositories.CartRepository
import ru.alexeyoss.features.cart.presentation.CartRouter
import ru.alexeyoss.foodie.mediators.cart.AdapterCartRouter
import ru.alexeyoss.foodie.mediators.cart.repositories.AdapterCartRepository

@Module
internal interface CartMediatorModule {
    @Binds
    fun bindCartRepository(impl: AdapterCartRepository): CartRepository

    @Binds
    fun bindCartRouter(impl: AdapterCartRouter): CartRouter

}