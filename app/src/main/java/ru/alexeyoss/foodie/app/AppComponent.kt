package ru.alexeyoss.foodie.app

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import ru.alexeyoss.core.common.ActiveActivityHolder
import ru.alexeyoss.data.categories.di.CategoriesDataModule
import ru.alexeyoss.features.categories.di.CategoriesComponent
import ru.alexeyoss.features.categories.di.CategoriesDeps
import ru.alexeyoss.features.categories.presentation.CategoryRouter
import ru.alexeyoss.foodie.MainActivity
import ru.alexeyoss.foodie.mediators.categories.di.CategoriesMediatorModule
import ru.alexeyoss.foodie.mediators.dishes.di.DishesMediatorModule
import ru.alexeyoss.foodie.navigation.NavigationModule
import ru.alexeyoss.foodie.permission.DefaultPermissionRationalFragment


@Component(
    modules = [
        AppModule::class
    ]
)
@AppScope
interface AppComponent  : CategoriesDeps {

    fun inject(activity: MainActivity)
    fun inject(fragment: DefaultPermissionRationalFragment)

    override fun categoryRouter(): CategoryRouter

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

}


@Module(
    includes = [
        CategoriesMediatorModule::class,
        DishesMediatorModule::class,
        NavigationModule::class,
        CategoriesDataModule::class,
        DishesMediatorModule::class
    ]
)
internal object AppModule {

    @Provides
    @AppScope
    internal fun provideActiveActivityHolder(): ActiveActivityHolder = ActiveActivityHolder()
}




