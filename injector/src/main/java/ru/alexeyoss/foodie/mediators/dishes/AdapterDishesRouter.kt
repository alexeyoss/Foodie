package ru.alexeyoss.foodie.mediators.dishes

import com.github.terrakok.cicerone.Router
import ru.alexeyoss.features.dishes.domain.models.UiDishDTO
import ru.alexeyoss.features.dishes.presentation.DishesRouter
import ru.alexeyoss.features.mainscreen.navigation.Screens
import javax.inject.Inject

class AdapterDishesRouter
@Inject constructor(
    private val router: Router
) : DishesRouter {
    override fun launchDishDetailsDialog(uiDish: UiDishDTO) {
        router.navigateTo(ru.alexeyoss.features.mainscreen.navigation.Screens.dishDetailsDialog(uiDish))
    }
}