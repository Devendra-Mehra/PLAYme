package com.playme.home.ui

import android.content.Context
import android.net.Uri
import com.google.android.exoplayer2.ExoPlaybackException
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory

class PlayerViewAdapter {
    companion object {
        private const val USER_AGENT = "exoplayer"
        private var playersMap: MutableMap<Int, SimpleExoPlayer> = mutableMapOf()
        private var currentVideoPlayer: Pair<Int, SimpleExoPlayer>? = null

        fun PlayerView.loadVideo(
            videoUrl: String,
            playerStateCallback: PlayerStateCallback,
            currentItemPosition: Int? = null
        ) {
            if (!playersMap.containsKey(currentItemPosition)) {
                val simpleExoPlayer = SimpleExoPlayer.Builder(context).build()
                simpleExoPlayer.apply {
                    playWhenReady = false
                    // When changing track, retain the latest frame instead of showing a black screen
                    setKeepContentOnPlayerReset(true)
                    setMediaSource(buildMediaSource(context, videoUrl))
                }
                this.player = simpleExoPlayer
                (this.player as SimpleExoPlayer).prepare()
                addListener(this.player as SimpleExoPlayer, playerStateCallback)


                if (playersMap.containsKey(currentItemPosition))
                    playersMap.remove(currentItemPosition)

                if (currentItemPosition != null) {
                    playersMap[currentItemPosition] = simpleExoPlayer
                }
            } else {

            }
        }

        private fun buildMediaSource(
            context: Context,
            mediaUrl: String
        ): MediaSource {
            val uri = Uri.parse(mediaUrl)
            val dataSourceFactory: DataSource.Factory =
                DefaultDataSourceFactory(context, USER_AGENT)
            return ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(MediaItem.fromUri(uri))
        }

        private fun addListener(
            playerView: SimpleExoPlayer,
            playerStateCallback: PlayerStateCallback
        ) {
            playerView.addListener(object : Player.EventListener {
                override fun onPlayerError(error: ExoPlaybackException) {
                    super.onPlayerError(error)
                }

                override fun onPlayWhenReadyChanged(playWhenReady: Boolean, reason: Int) {
                    super.onPlayWhenReadyChanged(playWhenReady, reason)
                }

                override fun onIsPlayingChanged(isPlaying: Boolean) {
                    super.onIsPlayingChanged(isPlaying)
                }

                override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
                    super.onMediaItemTransition(mediaItem, reason)
                }
            })
        }


        fun pauseCurrentPlayingVideo() {
            if (currentVideoPlayer != null) {
                currentVideoPlayer?.second?.playWhenReady = false
            }
        }

        fun playCurrentVideo() {
            if (currentVideoPlayer != null) {
                currentVideoPlayer?.second?.playWhenReady = true
            }
        }

        fun playNextVideo() {

        }

        fun releaseRecycledPlayers(index: Int) {
            playersMap[index]?.release()
        }

    }
}