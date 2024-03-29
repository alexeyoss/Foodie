package ru.alexeyoss.foodie.features.categories.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Component
import dagger.Module
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap
import ru.alexeyoss.foodie.core.common.di.modules.CoroutinesModule
import ru.alexeyoss.foodie.core.common.di.scope.PerScreen
import ru.alexeyoss.foodie.features.categories.domain.repositories.CategoriesRepository
import ru.alexeyoss.foodie.features.categories.presentation.CategoryRouter
import ru.alexeyoss.foodie.features.categories.presentation.categories.CategoriesFragment
import ru.alexeyoss.foodie.features.categories.presentation.categories.CategoriesViewModel

@[
PerScreen Component(
    modules = [CategoriesModule::class],
    dependencies = [CategoriesDeps::class]
)
]
interface CategoriesComponent {

    fun inject(categoriesFragment: CategoriesFragment)

    @Component.Builder
    interface Builder {

        fun deps(categoriesDeps: CategoriesDeps): Builder
        fun build(): CategoriesComponent
    }
}

@Module(
    includes = [CoroutinesModule::class]
)
internal interface CategoriesModule {

    @Binds
    @PerScreen
    @[IntoMap ClassKey(CategoriesViewModel::class)]
    fun bindCategoriesViewModel(categoriesViewModel: CategoriesViewModel): ViewModel
}

interface CategoriesDeps {
    val categoryRouter: CategoryRouter
    val categoriesRepository: CategoriesRepository
}
