package com.playme.utils

import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.playme.extension.getSnapPosition

class SnapOnScrollListener(
    private val snapHelper: SnapHelper,
    private val onSnapPositionChangeListener: ((position: Int) -> Unit)? = null,
    private val onScrollDraggingListener: (() -> Unit)? = null,
    private val onScrollDraggingStopListener: ((position: Int) -> Unit)? = null
) : RecyclerView.OnScrollListener() {

    private var snapPosition = RecyclerView.NO_POSITION

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        maybeNotifySnapPositionChange(recyclerView)
    }

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {

        if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
            notifyOnDragging()
        }
        if (newState == RecyclerView.SCROLL_STATE_SETTLING) {
            notifyOnDraggingStop(recyclerView)
        }
        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
            maybeNotifySnapPositionChange(recyclerView)
        }

    }

    private fun maybeNotifySnapPositionChange(recyclerView: RecyclerView) {
        val snapPosition = snapHelper.getSnapPosition(recyclerView)
        val snapPositionChanged = this.snapPosition != snapPosition
        if (snapPositionChanged) {
            onSnapPositionChangeListener?.invoke(snapPosition)
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