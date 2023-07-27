package ru.alexeyoss.core_ui.presentation

import android.content.res.Resources
import kotlin.math.roundToInt

/**
 * Convert [Int] value to Dp
 * */
val Int.dp: Int
    get() = (this * Resources.getSystem().displayMetrics.density).roundToInt()


/**
 * Convert [Float] value to Dp
 * */
val Float.dp: Int
    get() = (this * Resources.getSystem().displayMetrics.density).roundToInt()