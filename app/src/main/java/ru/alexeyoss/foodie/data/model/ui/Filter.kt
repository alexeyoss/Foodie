package ru.alexeyoss.foodie.data.model.ui

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Filter(
    val value: String,
    val displayText: String
) : Parcelable
