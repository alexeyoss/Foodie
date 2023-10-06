package ru.alexeyoss.features.dishes.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Component
import dagger.Module
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap
import ru.alexeyoss.features.dishes.domain.repositories.DishesRepository
import ru.alexeyoss.features.dishes.presentation.DishesRouter
import ru.alexeyoss.features.dishes.presentation.dishes.DishesFragment
import ru.alexeyoss.features.dishes.presentation.dishes.DishesViewModel
import ru.alexeyoss.foodie.core.common.di.modules.CoroutinesModule
import ru.alexeyoss.foodie.core.common.di.scope.PerScreen

@PerScreen
@Component(
    modules = [DishesModule::class],
    dependencies = [DishesDeps::class]
)
interface DishesComponent {

    fun inject(dishesFragment: DishesFragment)

    @Component.Builder
    interface Builder {

        fun deps(dishesDeps: DishesDeps): Builder
        fun build(): DishesComponent
    }
}

@Module(
    includes = [CoroutinesModule::class]
)
internal interface DishesModule {
    @Binds
    @PerScreen
    @[IntoMap ClassKey(DishesViewModel::class)]
    fun bindCategoriesViewModel(dishesViewModel: DishesViewModel): ViewModel
}

/**
 * Describe deps that we need from the AppComponent
 * */
interface DishesDeps {
    val dishesRouter: DishesRouter
    val dishesRepository: DishesRepository
}
