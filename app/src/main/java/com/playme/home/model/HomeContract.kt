package com.playme.home.model

interface HomeContract {
    interface Repository {
        fun getVideos(): List<String>
    }
}