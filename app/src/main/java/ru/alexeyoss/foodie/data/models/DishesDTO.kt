package ru.alexeyoss.foodie.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DishesDTO(
    val id: Int,
    val name: String,
    val price: Int,
    val weight: Int,
    val description: String,
    val imageUrl: String,
    val tegs: ArrayList<String>
) : Parcelable
