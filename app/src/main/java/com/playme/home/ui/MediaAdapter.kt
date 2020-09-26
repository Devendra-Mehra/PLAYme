package com.playme.home.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.exoplayer2.ui.PlayerView
import com.playme.R
import com.playme.extension.hide
import com.playme.extension.show
import com.playme.home.model.Video
import com.playme.home.ui.PlayerViewAdapter.Companion.loadVideo

class MediaAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var videos: MutableList<Video> = mutableListOf()
    private var onBookMarkClicked: ((videoUrl: String, toRemoveBookMark: Boolean) -> Unit)? = null
    private var onPlayerViewError: ((adapterPosition: Int) -> Unit)? = null

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

    fun pausePlayer(itemPosition: Int) {
        PlayerViewAdapter.pausePlayer(itemPosition)
    }

    fun setOnBookMarkClickedAction(
        onBookMarkClicked: ((
            videoUrl: String, toRemoveBookMark: Boolean
        ) -> Unit)? = null
    ) {
        this.onBookMarkClicked = onBookMarkClicked
    }

    fun setOnPlayerViewErrorAction(
        onPlayerViewError: ((adapterPosition: Int) -> Unit)? = null
    ) {
        this.onPlayerViewError = onPlayerViewError
    }

    inner class MediaViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val backgroundView: View = itemView.findViewById(R.id.view_background)
        private val playerView: PlayerView = itemView.findViewById(R.id.player_view)
        private val bookmark: AppCompatImageView = itemView.findViewById(R.id.book_mark)
        private val play: AppCompatImageView = itemView.findViewById(R.id.play)

        init {
            bookmark.setOnClickListener {
                val data = videos[adapterPosition]
                data.isBookmark = !data.isBookmark
                onBookMarkClicked?.invoke(data.videoUrl, data.isBookmark)
                toggleBookmarkIcon(data.isBookmark, bookmark)
            }
            play.setOnClickListener {
                play.hide()
                playerView.player?.play()
            }

            playerView.videoSurfaceView?.setOnClickListener {
                playerView.player?.pause()
                play.show()
            }
        }

        fun onBind(video: Video) {
            backgroundView.setBackgroundColor(video.dominatingColor)
            playerView.loadVideo(
                videoUrl = video.videoUrl,
                currentItemPosition = adapterPosition,
                playerStateCallback = object : PlayerStateCallback {

                    override fun onIsPlayingChanged(isPlaying: Boolean) {
                        if (isPlaying) {
                            play.hide()
                        } else {
                            play.show()
                        }
                    }

                    override fun onPlayerError() {
                        if (adapterPosition + 1 == videos.size) {
                            onPlayerViewError?.invoke(adapterPosition - 1)
                        } else {
                            onPlayerViewError?.invoke(adapterPosition + 1)
                        }
                    }
                }
            )
            if (playerView.player?.isPlaying == true) {
                play.hide()
            } else {
                play.show()
            }
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