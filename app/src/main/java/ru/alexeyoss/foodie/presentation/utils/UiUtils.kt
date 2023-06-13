package ru.alexeyoss.foodie.presentation

fun <T> lazyUnsafe(block: () -> T) = lazy(LazyThreadSafetyMode.NONE) { block() }