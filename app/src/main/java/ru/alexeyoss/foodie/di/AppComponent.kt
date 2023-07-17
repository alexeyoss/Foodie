package ru.alexeyoss.foodie.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Component
import dagger.Module
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap
import ru.alexeyoss.core.common.di.MainToolsComponent
import ru.alexeyoss.core.common.di.MainToolsProvider
import ru.alexeyoss.core.common.di.scope.PerActivity
import ru.alexeyoss.core.common.di.scope.PerApplication
import ru.alexeyoss.data.di.DataComponent
import ru.alexeyoss.data.di.DataProvider
import ru.alexeyoss.features.cart.di.CartDeps
import ru.alexeyoss.features.categories.di.CategoriesDeps
import ru.alexeyoss.features.dishes.di.DishesDeps
import ru.alexeyoss.foodie.FoodieApp
import ru.alexeyoss.foodie.mediators.cart.di.CartMediatorModule
import ru.alexeyoss.foodie.mediators.categories.di.CategoriesMediatorModule
import ru.alexeyoss.foodie.mediators.dishes.di.DishesMediatorModule
import ru.alexeyoss.foodie.activity.MainActivity
import ru.alexeyoss.foodie.activity.MainActivityViewModel
import ru.alexeyoss.services.navigation.di.NavigationComponent
import ru.alexeyoss.services.navigation.di.NavigationProvider
import ru.alexeyoss.services.permission.di.PermissionComponent
import ru.alexeyoss.services.permission.di.PermissionProvider

interface AppComponentProvider :
    MainToolsProvider,
    DataProvider,
    NavigationProvider,
    PermissionProvider,
    CategoriesDeps,
    DishesDeps,
    CartDeps


@[PerApplication Component(
    modules = [
        AppModule::class,
        CategoriesMediatorModule::class,
        DishesMediatorModule::class,
        CartMediatorModule::class,
    ],
    dependencies = [
        MainToolsProvider::class,
        DataProvider::class,
        NavigationProvider::class,
        PermissionProvider::class,
    ]
)]

interface AppComponent : AppComponentProvider {

    fun inject(app: FoodieApp)

    fun inject(mainActivity: MainActivity)

    class Initializer private constructor() {
        companion object {

            fun init(app: FoodieApp): AppComponent {

                val mainToolsProvider = MainToolsComponent.Initializer
                    .init(app)

                val permissionComponent = PermissionComponent.Initializer
                    .init(mainToolsProvider)

                val dataComponent = DataComponent.Initializer
                    .init()

                val navigationProvider = NavigationComponent.Initializer
                    .init()

                return DaggerAppComponent.builder()
                    .mainToolsProvider(mainToolsProvider)
                    .dataProvider(dataComponent)
                    .navigationProvider(navigationProvider)
                    .permissionProvider(permissionComponent)
                    .build()

            }
        }
    }
}

@Module
internal interface AppModule {
    @Binds
    @PerActivity
    @[IntoMap ClassKey(MainActivityViewModel::class)]
    fun bindMainActivityViewModel(mainActivityViewModel: MainActivityViewModel): ViewModel
}



