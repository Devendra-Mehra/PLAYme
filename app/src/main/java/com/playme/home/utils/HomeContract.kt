package com.playme.home.utils

class HomeContract {
    interface Repository {
        fun getVideos(): List<String>
    }
}