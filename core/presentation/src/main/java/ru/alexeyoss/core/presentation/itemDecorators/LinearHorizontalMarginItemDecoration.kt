package ru.alexeyoss.core.presentation.itemDecorators

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class LinearHorizontalMarginItemDecoration(
    private val horizontalMargin: Int,
    private val firstItemTopMargin: Int = NOT_SET,
    private val lastItemMargin: Int = NOT_SET
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State
    ) {
        with(outRect) {

            left = if (isFirstItem(parent, view) && firstItemTopMargin != NOT_SET) firstItemTopMargin else 0
            right = if (lastItemMargin == NOT_SET) horizontalMargin
            else {
                if (isLastItem(parent, view, state)) {
                    lastItemMargin
                } else {
                    horizontalMargin
                }
            }
        }
    }

    private fun isFirstItem(parent: RecyclerView, view: View) =
        parent.getChildAdapterPosition(view) == 0

    private fun isLastItem(parent: RecyclerView, view: View, state: RecyclerView.State) =
        parent.getChildAdapterPosition(view) == state.itemCount - 1

    companion object {
        private const val NOT_SET = -1
    }

}