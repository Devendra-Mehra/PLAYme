package com.playme.home.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.PlayerView
import com.playme.R
import com.playme.home.model.Video
import com.playme.home.ui.PlayerViewAdapter.Companion.loadVideo

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
        PlayerViewAdapter.releaseRecycledPlayers(holder.adapterPosition)
        super.onViewRecycled(holder)
    }

    fun setData(videos: List<Video>) {
        this.videos.addAll(videos)
    }

    fun pausePlayer(itemPosition : Int) {
        PlayerViewAdapter.pausePlayer(itemPosition)
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
                onBookMarkClicked?.invoke(data.videoUrl, data.isBookmark)
                toggleBookmarkIcon(data.isBookmark, bookmark)
            }

        }

        fun onBind(video: Video) {
            playerView.loadVideo(
                videoUrl = video.videoUrl,
                currentItemPosition = adapterPosition,
                playerStateCallback = object : PlayerStateCallback{
                    override fun onStartedPlaying(player: Player) {
                        TODO("Not yet implemented")
                    }

                    override fun onFinishedPlaying(player: Player) {
                        TODO("Not yet implemented")
                    }

                    override fun onPlayerError(exoPlaybackException: String) {
                        TODO("Not yet implemented")
                    }

                }
            )
            toggleBookmarkIcon(video.isBookmark, bookmark)
        }

        fun stopPlayer() {
            playerView.onPause()
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