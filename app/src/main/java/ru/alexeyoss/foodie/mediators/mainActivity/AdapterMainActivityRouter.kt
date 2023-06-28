package ru.alexeyoss.foodie.mediators.mainActivity

import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.Screen
import ru.alexeyoss.foodie.MainActivityRouter
import javax.inject.Inject

class AdapterMainActivityRouter @Inject constructor(
    private val router: Router
) : MainActivityRouter {

    override fun navigateTo(screen: Screen) {
        router.navigateTo(screen)
    }

}