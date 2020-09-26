package com.playme.home.ui


interface PlayerStateCallback {

    fun onIsPlayingChanged(isPlaying: Boolean)

    fun onPlayerError()
}