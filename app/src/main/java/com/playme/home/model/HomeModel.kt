package com.playme.home.model

import com.playme.core.PersistenceContract
import com.playme.home.utils.HomeContract
import javax.inject.Inject

class HomeModel @Inject constructor(
    private val repository: HomeContract.Repository,
    private val persistence: PersistenceContract
) {

    fun getVideos(): List<Video> {
        val videos: ArrayList<Video> = arrayListOf()
        val bookMarked = persistence.getBookMarked()
        repository.getVideos().map {
            if (bookMarked.isNullOrEmpty()) {
                videos.add(Video(media_url = it, isBookmark = false))
            } else {
                videos.add(Video(media_url = it, isBookmark = bookMarked.contains(it)))
            }
        }
        return videos
    }

}