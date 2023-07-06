package ru.alexeyoss.foodie.mediators.categories

import com.github.terrakok.cicerone.Router
import ru.alexeyoss.features.categories.presentation.CategoryRouter
import ru.alexeyoss.features.mainscreen.navigation.Screens
import javax.inject.Inject

class AdapterCategoriesRouter
@Inject constructor(
    private val router: Router
) : CategoryRouter {

    override fun launchDishesScreen(categoryName: String) {
        router.navigateTo(ru.alexeyoss.features.mainscreen.navigation.Screens.dishes(categoryName))
    }

    override fun goBack() {
        router.finishChain()
    }
}