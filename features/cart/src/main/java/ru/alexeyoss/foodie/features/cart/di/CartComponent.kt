package ru.alexeyoss.foodie.features.cart.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Component
import dagger.Module
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap
import ru.alexeyoss.foodie.core.common.di.modules.CoroutinesModule
import ru.alexeyoss.foodie.core.common.di.scope.PerScreen
import ru.alexeyoss.foodie.features.cart.domain.repositories.CartRepository
import ru.alexeyoss.foodie.features.cart.presentation.CartRouter
import ru.alexeyoss.foodie.features.cart.presentation.cart.CartFragment
import ru.alexeyoss.foodie.features.cart.presentation.cart.CartViewModel

@[
PerScreen Component(
    modules = [CartModule::class],
    dependencies = [CartDeps::class]
)
]
interface CartComponent {
    fun inject(cartFragment: CartFragment)

    @Component.Builder
    interface Builder {

        fun deps(cartDeps: CartDeps): Builder
        fun build(): CartComponent
    }
}

@Module(
    includes = [CoroutinesModule::class]
)
internal interface CartModule {
    @Binds
    @PerScreen
    @[IntoMap ClassKey(CartViewModel::class)]
    fun bindCartViewModel(cartViewModel: CartViewModel): ViewModel
}

interface CartDeps {
    val cartRouter: CartRouter
    val cartRepository: CartRepository
}
