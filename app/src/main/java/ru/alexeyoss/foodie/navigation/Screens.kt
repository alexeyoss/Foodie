package ru.alexeyoss.foodie.navigation

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import com.github.terrakok.cicerone.androidx.ActivityScreen
import com.github.terrakok.cicerone.androidx.FragmentScreen
import ru.alexeyoss.features.cart.presentation.CartFragment
import ru.alexeyoss.features.categories.presentation.categories.CategoriesFragment
import ru.alexeyoss.features.dishes.domain.models.UiDishDTO
import ru.alexeyoss.features.dishes.presentation.dish_details.DishDetailsDialogFragment
import ru.alexeyoss.features.dishes.presentation.dishes.DishesFragment
import ru.alexeyoss.foodie.BuildConfig
import ru.alexeyoss.foodie.permission.DefaultPermissionRationalFragment

object Screens {

    fun categories() = FragmentScreen {
        CategoriesFragment.getNewInstance()
    }

    fun dishes(categoryName: String) = FragmentScreen {
        DishesFragment.getNewInstance(categoryName)
    }

    fun cart() = FragmentScreen() {
        CartFragment.getNewInstance()
    }

    fun dishDetailsDialog(uiDish: UiDishDTO) = FragmentScreen {
        DishDetailsDialogFragment.getNewInstance(uiDish)
    }

    fun settingsActivity() = ActivityScreen {
        Intent(
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.fromParts(
                "package", BuildConfig.APPLICATION_ID,
                null
            )
        ).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)


    }

    // TODO implement Dialog fragments support in Cicerone https://github.com/terrakok/Cicerone/discussions/136
    fun permissionExplanationDialog(customPermissionsRationalStr: String) = FragmentScreen {
        DefaultPermissionRationalFragment.getNewInstance(
            customPermissionsRationalStr
        )
    }
}