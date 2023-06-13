package ru.alexeyoss.foodie.data.model.network

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


data class DishesList(
    @SerializedName("dishes")
    @Expose
    val dishes: ArrayList<DishDTO>
)
