package ru.alexeyoss.foodie.mediators.cart

import com.github.terrakok.cicerone.Router
import ru.alexeyoss.features.cart.presentation.CartRouter
import ru.alexeyoss.foodie.navigation.Screens
import javax.inject.Inject

class AdapterCartRouter
@Inject
constructor(
    private val router: Router
) : CartRouter {
    override fun goBack() {
        router.navigateTo(Screens.categories())
    }
}