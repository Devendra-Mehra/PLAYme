package com.playme.home.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.playme.R
import com.playme.home.model.Video

class MediaAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var videos: MutableList<Video> = mutableListOf()
    private var onBookMarkClicked: ((videoUrl: String, toRemoveBookMark: Boolean) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MediaViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_media, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return videos.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MediaViewHolder) {
            holder.onBind(videos[position])
        }
    }

    override fun onViewRecycled(holder: RecyclerView.ViewHolder) {
        super.onViewRecycled(holder)
    }

    fun setData(videos: List<Video>) {
        this.videos.addAll(videos)
    }

    fun setonBookMarkClickedData(
        onBookMarkClicked: ((
            videoUrl: String, toRemoveBookMark: Boolean
        ) -> Unit)? = null
    ) {
        this.onBookMarkClicked = onBookMarkClicked
    }

}