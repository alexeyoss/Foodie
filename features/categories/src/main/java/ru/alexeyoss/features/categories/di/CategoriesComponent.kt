package ru.alexeyoss.features.categories.di

import androidx.annotation.RestrictTo
import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Component
import dagger.Module
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap
import ru.alexeyoss.features.categories.presentation.CategoryRouter
import ru.alexeyoss.features.categories.presentation.categories.CategoriesFragment
import ru.alexeyoss.features.categories.presentation.categories.CategoriesViewModel
import javax.inject.Scope

@Scope
@MustBeDocumented
@Retention(AnnotationRetention.BINARY)
annotation class CategoriesFeatureScope

@[CategoriesFeatureScope
Component(
    modules = [CategoriesModule::class],
    dependencies = [CategoriesDeps::class]
)]
interface CategoriesComponent {

    fun inject(categoriesFragment: CategoriesFragment)

    @Component.Builder
    interface Builder {

        fun deps(categoriesDeps: CategoriesDeps): Builder
        fun build(): CategoriesComponent
    }

}

@Module
interface CategoriesModule {

    @Binds
    @[IntoMap ClassKey(CategoriesViewModel::class)]
    fun bindCategoriesViewModel(categoriesViewModel: CategoriesViewModel): ViewModel
}

interface CategoriesDeps {
    fun categoryRouter(): CategoryRouter
}


