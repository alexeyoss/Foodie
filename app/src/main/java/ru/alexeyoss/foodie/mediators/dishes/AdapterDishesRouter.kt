package ru.alexeyoss.foodie.mediators.dishes

import com.github.terrakok.cicerone.Router
import ru.alexeyoss.features.dishes.presentation.DishesRouter
import ru.alexeyoss.foodie.navigation.Screens
import javax.inject.Inject

class AdapterDishesRouter
@Inject constructor(
    private val router: Router,
) : DishesRouter {

    override fun goBack() {
        router.navigateTo(Screens.categories())
    }
}