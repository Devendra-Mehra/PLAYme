package com.playme.home.model

interface HomeModel {
    fun getVideos(callback: (List<Video>) -> Unit)
    fun storeBookMark(videoUrl: String)
    fun removeBookMark(videoUrl: String)
    fun clearResource()
}