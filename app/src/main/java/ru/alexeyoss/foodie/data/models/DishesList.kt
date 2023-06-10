package ru.alexeyoss.foodie.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DishesList(
    val dishes: ArrayList<DishesDTO>
) : Parcelable
