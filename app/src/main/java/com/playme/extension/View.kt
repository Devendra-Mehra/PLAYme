package com.playme.extension

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.playme.utils.SnapOnScrollListener


fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.GONE
}

fun Context.toast(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}

fun SnapHelper.getSnapPosition(recyclerView: RecyclerView): Int {
    val layoutManager = recyclerView.layoutManager ?: return RecyclerView.NO_POSITION
    val snapView = findSnapView(layoutManager) ?: return RecyclerView.NO_POSITION
    return layoutManager.getPosition(snapView)
}

fun RecyclerView.attachSnapHelperWithListener(
    snapHelper: SnapHelper,
    onSnapPositionChangeListener: ((position: Int, isScrolledUpEnabled: Boolean) -> Unit)? = null,
    onScrollDragging: (() -> Unit)? = null,
    onScrollDraggingStopListener: ((position: Int) -> Unit)? = null
) {
    snapHelper.attachToRecyclerView(this)
    val snapOnScrollListener =
        SnapOnScrollListener(
            snapHelper, onSnapPositionChangeListener,
            onScrollDragging, onScrollDraggingStopListener
        )
    addOnScrollListener(snapOnScrollListener)
}
