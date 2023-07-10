@file:Suppress("DEPRECATION", "UNCHECKED_CAST")

package ru.alexeyoss.core_ui.presentation

import androidx.fragment.app.Fragment

/**
 * Default arg name for holding screen args in fragments
 */
const val ARG_SCREEN = "SCREEN"

/**
 * Get screen args attached to the [Fragment].
 */
fun <T : BaseScreen> Fragment.args(): T {
    return requireArguments().getParcelable(ARG_SCREEN) as? T ?: throw IllegalStateException("Screen args don't exist")
}