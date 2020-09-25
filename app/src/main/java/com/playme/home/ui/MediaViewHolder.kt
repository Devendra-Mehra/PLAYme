package com.playme.home.ui

import android.content.Context
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.playme.R
import com.playme.home.model.Video

class MediaViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val root: ConstraintLayout = itemView.findViewById(R.id.media_item_root)
    private val playerView: ConstraintLayout = itemView.findViewById(R.id.player_view)
    private val bookmark: AppCompatImageView = itemView.findViewById(R.id.book_mark)
    private val play: AppCompatImageView = itemView.findViewById(R.id.play)
    private val context: Context = root.context

    fun onBind(video: Video) {

    }

}