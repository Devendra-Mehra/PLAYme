package com.playme.home.model

class HomeContract {
    interface Repository {
        fun getVideos(): List<String>
    }
}