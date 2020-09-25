package com.playme.home.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.exoplayer2.ui.PlayerView
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

    fun setOnBookMarkClickedAction(
        onBookMarkClicked: ((
            videoUrl: String, toRemoveBookMark: Boolean
        ) -> Unit)? = null
    ) {
        this.onBookMarkClicked = onBookMarkClicked
    }


    inner class MediaViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val root: ConstraintLayout = itemView.findViewById(R.id.media_item_root)
        private val playerView: PlayerView = itemView.findViewById(R.id.player_view)
        private val bookmark: AppCompatImageView = itemView.findViewById(R.id.book_mark)
        private val play: AppCompatImageView = itemView.findViewById(R.id.play)
        private val context: Context = root.context

        init {
            bookmark.setOnClickListener {
                val data = videos[adapterPosition]
                data.isBookmark = !data.isBookmark
                onBookMarkClicked?.invoke(data.media_url, data.isBookmark)
                toggleBookmarkIcon(data.isBookmark, bookmark)
            }

        }

        fun onBind(video: Video) {

            toggleBookmarkIcon(video.isBookmark, bookmark)
        }

        private fun toggleBookmarkIcon(isBookmarked: Boolean, bookMarkView: AppCompatImageView) {
            if (isBookmarked) {
                bookMarkView.setImageDrawable(
                    ContextCompat.getDrawable(bookMarkView.context, R.drawable.ic_bookmarked)
                )
            } else {
                bookMarkView.setImageDrawable(
                    ContextCompat.getDrawable(bookMarkView.context, R.drawable.ic_bookmark)
                )
            }
        }

    }
}