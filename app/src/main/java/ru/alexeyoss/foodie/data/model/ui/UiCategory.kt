package ru.alexeyoss.foodie.data.model.ui

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import ru.alexeyoss.foodie.data.model.network.CategoryDTO

@Parcelize
data class UiCategory(
    val id: Int,
    val name: String,
    val image_url: String
) : Parcelable
