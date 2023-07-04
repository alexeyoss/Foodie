package ru.alexeyoss.features.cart.di

import dagger.Component
import ru.alexeyoss.features.cart.presentation.CartFragment
import javax.inject.Singleton

@Singleton
@Component(
    modules = [CartModule::class]
)
interface CartComponent {

    fun inject(cartFragment: CartFragment)
}
