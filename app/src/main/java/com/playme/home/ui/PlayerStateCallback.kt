package com.playme.home.ui

import com.google.android.exoplayer2.Player

interface PlayerStateCallback {

    fun onStartedPlaying(player: Player)

    fun onFinishedPlaying(player: Player)

    fun onPlayerError(exoPlaybackException: String)
}