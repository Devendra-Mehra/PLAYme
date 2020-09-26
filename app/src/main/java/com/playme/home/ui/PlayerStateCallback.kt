package com.playme.home.ui

import com.google.android.exoplayer2.Player

interface PlayerStateCallback {

    fun onIsPlayingChanged(isPlaying: Boolean)

    fun onPlayerError(exoPlaybackException: String)
}