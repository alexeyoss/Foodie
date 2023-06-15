package ru.alexeyoss.foodie

import android.os.Parcelable
import androidx.annotation.IdRes

interface NavComponentRouter {

    fun launchScreen(@IdRes destinationId: Int, arg: Parcelable? = null)

    fun goBack()
}