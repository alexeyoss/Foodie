package ru.alexeyoss.core_ui.presentation.toolbar

import androidx.annotation.IdRes
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager.OnBackStackChangedListener
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding
import android.widget.Toolbar

/**
 * Abstract class for flexible handling custom [Toolbar].
 * Use [addToolbarStateListener] and [removeToolbarStateListener] in Activity methods [FragmentActivity.onCreate]
 * and [FragmentActivity.onDestroy] accordingly.
 *  @see ToolbarStateOwner
 *  @see BaseToolbarState
 * */
abstract class ToolbarHandler<A : FragmentActivity, T : BaseToolbarState>
protected constructor(
    private val activity: A, @IdRes private val containerId: Int
) {

    val lifeCycleObserver: DefaultLifecycleObserver = object : DefaultLifecycleObserver {
        override fun onCreate(owner: LifecycleOwner) {
            addToolbarStateListener()
        }

        override fun onDestroy(owner: LifecycleOwner) {
            removeToolbarStateListener()
        }
    }

    abstract val binding: ViewBinding

    private val backStackChangeListener = OnBackStackChangedListener { getAndUpdateCurrentToolbarState() }

    fun addToolbarStateListener() {
        activity.supportFragmentManager.addOnBackStackChangedListener(backStackChangeListener)
    }

    fun removeToolbarStateListener() {
        activity.supportFragmentManager.removeOnBackStackChangedListener(backStackChangeListener)
    }

    /**
     * Get current [FoodieToolbarStates] relevant for focused fragment from host container and update UI state
     * */
    private fun getAndUpdateCurrentToolbarState() {
        val currentFragment = activity.supportFragmentManager.findFragmentById(containerId)
        if (currentFragment is ToolbarStateOwner) {
            val toolbarState = currentFragment.getToolbarState()
            updateToolbarView(toolbarState as T)
        }
    }

    /**
     * Override method for toolbar in certain Activity. Update view components depending on our custom [FoodieToolbarStates]
     * */
    protected abstract fun updateToolbarView(toolbarState: T)

}