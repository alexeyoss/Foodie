package ru.alexeyoss.foodie.mediators.categories

import ru.alexeyoss.features.categories.presentation.CategoryRouter
import ru.alexeyoss.features.dishes.R
import ru.alexeyoss.foodie.NavComponentRouter
import javax.inject.Inject

class AdapterCategoryRouter
@Inject constructor(
    private val navComponentRouter: NavComponentRouter
) : CategoryRouter {

    override fun launchDishesScreen() {
        navComponentRouter.launchScreen(
            destinationId = R.id.dishesFragment,
            arg = null
        )
    }

    override fun goBack() {
        navComponentRouter.goBack()
    }
}