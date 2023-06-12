package ru.alexeyoss.foodie.data.model.network

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DishesList(
    val dishes: ArrayList<DishDTO>
) : Parcelable
