package ru.alexeyoss.core_ui.presentation

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

fun <T> Flow<T>.collectOnLifecycle(
    lifecycleOwner: LifecycleOwner,
    state: Lifecycle.State,
    collector: (T) -> Unit
) {
    lifecycleOwner.lifecycleScope.launch {
        lifecycleOwner.repeatOnLifecycle(state) {
            collect {
                collector(it)
            }
        }
    }
}

fun <T> Flow<T>.collectOnLifecycle(
    lifecycleOwner: AppCompatActivity,
    collector: (T) -> Unit
) {
    collectOnLifecycle(lifecycleOwner, Lifecycle.State.STARTED, collector)
}

fun <T> Flow<T>.collectOnLifecycle(
    lifecycleOwner: Fragment,
    collector: (T) -> Unit
) {
    collectOnLifecycle(lifecycleOwner, Lifecycle.State.RESUMED, collector)
}
