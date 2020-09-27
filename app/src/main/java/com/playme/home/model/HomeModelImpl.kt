package com.playme.home.model

import android.graphics.Bitmap
import android.media.MediaMetadataRetriever
import com.playme.core.PersistenceContract
import java.util.concurrent.ExecutorService
import javax.inject.Inject

class HomeModelImpl @Inject constructor(
    private val repository: HomeContract.Repository,
    private val persistence: PersistenceContract,
    private val executorService: ExecutorService
) : HomeModel {

    override fun getVideos(callback: (List<Video>) -> Unit) {
        executorService.execute {
            val videos: ArrayList<Video> = arrayListOf()
            val bookMarked = persistence.getBookMarked()
            repository.getVideos().map {
                if (bookMarked.isNullOrEmpty()) {
                    videos.add(
                        Video(
                            videoUrl = it, isBookmark = false,
                            dominatingColor = getDominantColor(retrieveVideoFrameFromVideo(it))
                        )
                    )
                } else {
                    videos.add(
                        Video(
                            videoUrl = it, isBookmark = bookMarked.contains(it),
                            dominatingColor = getDominantColor(retrieveVideoFrameFromVideo(it))
                        )
                    )
                }
            }
            callback.invoke(videos)
        }
    }

    override fun storeBookMark(videoUrl: String) {
        persistence.storeBookMark(videoUrl)
    }

    override fun removeBookMark(videoUrl: String) {
        persistence.removeBookMark(videoUrl)
    }

    override fun clearResource() {
        if (!executorService.isShutdown) {
            executorService.shutdown()
        }
    }

    private fun retrieveVideoFrameFromVideo(videoPath: String): Bitmap? {
        var bitmap: Bitmap? = null
        var mediaMetadataRetriever: MediaMetadataRetriever? = null
        try {
            mediaMetadataRetriever = MediaMetadataRetriever()
            mediaMetadataRetriever.setDataSource(videoPath)

            bitmap = mediaMetadataRetriever.getFrameAtTime(
                1,
                MediaMetadataRetriever.OPTION_CLOSEST
            )
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            mediaMetadataRetriever?.release()
        }
        return bitmap
    }

    private fun getDominantColor(bitmap: Bitmap?): Int {
        var newBitmap: Bitmap? = null
        var color = 8679796
        bitmap?.let {
            newBitmap =
                Bitmap.createScaledBitmap(it, 1, 1, true)
            color = (newBitmap as Bitmap).getPixel(0, 0)
        }
        newBitmap?.recycle()
        return color
    }
}