package ru.alexeyoss.foodie

import android.content.Intent
import com.github.terrakok.cicerone.Screen

interface MainActivityRouter {

    fun navigateTo(screen: Screen)
}