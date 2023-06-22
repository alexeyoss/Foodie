package ru.alexeyoss.foodie.navigation

import com.github.terrakok.cicerone.androidx.FragmentScreen
import ru.alexeyoss.features.cart.presentation.CartFragment
import ru.alexeyoss.features.categories.presentation.categories.CategoriesFragment
import ru.alexeyoss.features.dishes.domain.models.UiDishDTO
import ru.alexeyoss.features.dishes.presentation.dish_details.DishDetailsDialogFragment
import ru.alexeyoss.features.dishes.presentation.dishes.DishesFragment

object Screens {

    fun categories() = FragmentScreen {
        CategoriesFragment.getNewInstance()
    }

    fun dishes(categoryName: String) = FragmentScreen() {
        DishesFragment.getNewInstance(categoryName)
    }

    fun cart() = FragmentScreen() {
        CartFragment.getNewInstance()
    }

    fun dishDetailsDialog(uiDish: UiDishDTO) = FragmentScreen {
        DishDetailsDialogFragment.getNewInstance(uiDish)
    }
}