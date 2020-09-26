package com.playme.utils

import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.playme.extension.getSnapPosition

class SnapOnScrollListener(
    private val snapHelper: SnapHelper,
    private val onSnapPositionChangeListener: ((position: Int, isScrolledUpEnabled: Boolean) -> Unit)? = null,
    private val onScrollDraggingListener: (() -> Unit)? = null,
    private val onScrollDraggingStopListener: ((position: Int) -> Unit)? = null
) : RecyclerView.OnScrollListener() {

    private var snapPosition = RecyclerView.NO_POSITION

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        if (dy > 0) {
            //Scrolled Downwards
            notifySnapPositionChange(recyclerView, false)
        } else {
            //Scrolled Upwards
            notifySnapPositionChange(recyclerView, true)
        }
    }

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
            notifyOnDragging()
        }
        if (newState == RecyclerView.SCROLL_STATE_SETTLING) {
            notifyOnDraggingStop(recyclerView)
        }
    }

    private fun notifySnapPositionChange(
        recyclerView: RecyclerView,
        isScrolledUpEnabled: Boolean
    ) {
        val snapPosition = snapHelper.getSnapPosition(recyclerView)
        val snapPositionChanged = this.snapPosition != snapPosition
        if (snapPositionChanged) {
            onSnapPositionChangeListener?.invoke(snapPosition, isScrolledUpEnabled)
            this.snapPosition = snapPosition
        }
    }

    private fun notifyOnDragging() {
        onScrollDraggingListener?.invoke()
    }

    private fun notifyOnDraggingStop(recyclerView: RecyclerView) {
        val snapPosition = snapHelper.getSnapPosition(recyclerView)
        onScrollDraggingStopListener?.invoke(snapPosition)
    }
}