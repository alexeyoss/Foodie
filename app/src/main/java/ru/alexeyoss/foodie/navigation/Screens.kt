package ru.alexeyoss.foodie.navigation

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import com.github.terrakok.cicerone.androidx.ActivityScreen
import com.github.terrakok.cicerone.androidx.FragmentScreen
import ru.alexeyoss.features.cart.presentation.cart.CartFragment
import ru.alexeyoss.features.categories.presentation.categories.CategoriesFragment
import ru.alexeyoss.features.dishes.presentation.dishes.DishesFragment
import ru.alexeyoss.foodie.BuildConfig

object Screens {

    /**
     * [CategoriesFragment] is the start screen and exit from the app. If local backstack on BottomNavigationView
     * sections is empty then goBack operation return to start screen [CategoriesFragment]
     * */
    fun categories() = FragmentScreen {
        CategoriesFragment.getNewInstance()
    }

    fun dishes(categoryName: String) = FragmentScreen {
        DishesFragment.getNewInstance(categoryName)
    }

    fun cart() = FragmentScreen() {
        CartFragment.getNewInstance()
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

}