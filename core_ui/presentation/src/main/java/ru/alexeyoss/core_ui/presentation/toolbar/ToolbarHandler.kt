package ru.alexeyoss.core_ui.presentation.toolbar

import androidx.annotation.IdRes
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager.OnBackStackChangedListener
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.viewbinding.ViewBinding

/**
 * Abstract class for flexible handling custom [ToolbarStates].
 * Use [addToolbarStateListener] and [removeToolbarStateListener] in Activity methods [FragmentActivity.onCreate]
 * and [FragmentActivity.onDestroy] accordingly.
 *  @see ToolbarStateHolder
 * */
abstract class ToolbarHandler<A : FragmentActivity, T : BaseToolbarState>
protected constructor(
    private val activity: A, @IdRes private val containerId: Int
) {

    abstract val lifeCycleObserver: DefaultLifecycleObserver

    abstract val binding: ViewBinding

    private val backStackChangeListener = OnBackStackChangedListener { checkCurrentToolbarState() }

    fun addToolbarStateListener() {
        activity.supportFragmentManager.addOnBackStackChangedListener(backStackChangeListener)
    }

    fun removeToolbarStateListener() {
        activity.supportFragmentManager.removeOnBackStackChangedListener(backStackChangeListener)
    }

    /**
     * Get current [ToolbarStates] relevant for focused fragment from host container and update UI state
     * */
    private fun checkCurrentToolbarState() {
        val currentFragment = activity.supportFragmentManager.findFragmentById(containerId)
        if (currentFragment is ToolbarStateHolder) {
            val toolbarState = currentFragment.getToolbarState()
            updateToolbarView(toolbarState as T)
        }
    }

    /**
     * Override method for toolbar in certain Activity. Update view components depending on our custom [ToolbarStates]
     * */
    protected abstract fun updateToolbarView(toolbarState: T)

}