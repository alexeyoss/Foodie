package ru.alexeyoss.foodie.presentation.utils

import android.content.res.Resources

fun <T> lazyUnsafe(block: () -> T) = lazy(LazyThreadSafetyMode.NONE) { block() }

fun Int.toDp(): Int = (this / Resources.getSystem().displayMetrics.density).toInt()
fun Int.toPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()
