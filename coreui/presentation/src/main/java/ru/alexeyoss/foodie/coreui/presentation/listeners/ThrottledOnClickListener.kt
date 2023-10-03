package ru.alexeyoss.core_ui.presentation.listeners

import android.os.SystemClock
import android.view.View
import java.util.WeakHashMap

/** Default timeoutMillis for  [ThrottledOnClickListener]*/
private const val defaultTimeoutMillis = 500L

/**
 * Click listener with debounce effect
 * @param timeoutMillis default value 500L
 * */
@Deprecated("This type of debounce is a peace of shit! Don't use!")
class ThrottledOnClickListener(
    private val timeoutMillis: Long = defaultTimeoutMillis,
    private val onClick: (view: View, clicks: Int) -> Unit
) : View.OnClickListener {
    private val lastClickMap: MutableMap<View, Long> = WeakHashMap()
    private var clickCounter = 0

    override fun onClick(clickedView: View) {
        clickCounter++
        val previousClickTimestamp = lastClickMap[clickedView]
        val currentTimestamp = SystemClock.uptimeMillis()

        lastClickMap[clickedView] = currentTimestamp
        if (previousClickTimestamp == null || currentTimestamp - previousClickTimestamp.toLong() > timeoutMillis) {
            onClick.invoke(clickedView, clickCounter)
            clickCounter = 0
        }
    }
}

