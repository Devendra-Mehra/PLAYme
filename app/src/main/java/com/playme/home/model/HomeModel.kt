package com.playme.home.model

import com.playme.core.PersistenceContract
import java.util.concurrent.ExecutorService
import javax.inject.Inject

class HomeModel @Inject constructor(
    private val repository: HomeContract.Repository,
    private val persistence: PersistenceContract,
    private val executorService: ExecutorService
) {

    fun getVideos(callback: (ArrayList<Video>) -> Unit) {
        executorService.execute {
            val videos: ArrayList<Video> = arrayListOf()
            val bookMarked = persistence.getBookMarked()
            repository.getVideos().map {
                if (bookMarked.isNullOrEmpty()) {
                    videos.add(Video(videoUrl = it, isBookmark = false))
                } else {
                    videos.add(Video(videoUrl = it, isBookmark = bookMarked.contains(it)))
                }
            }
            callback.invoke(videos)
        }
    }

    fun storeBookMark(videoUrl: String) {
        persistence.storeBookMark(videoUrl)
    }

    fun removeBookMark(videoUrl: String) {
        persistence.removeBookMark(videoUrl)
    }

    fun clearResource() {
        if (!executorService.isShutdown) {
            executorService.shutdown()
        }
    }

}