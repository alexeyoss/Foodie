package ru.alexeyoss.core_ui.presentation.toolbar

import androidx.fragment.app.Fragment

/**
 * Fragment interface  for holding certain [ToolbarStates]
 * @see ToolbarStates
 * */
interface ToolbarStateHolder {
    fun getToolbarState(): ToolbarStates
}
