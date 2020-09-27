package com.playme.utils

import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.playme.extension.getSnapPosition

class SnapOnScrollListener(
    private val snapHelper: SnapHelper,
    private val onSnapPositionChangeListener:
    ((position: Int, isScrolledUpEnabled: Boolean) -> Unit)? = null
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


}