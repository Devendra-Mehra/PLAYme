package com.playme.home.model

import android.content.Context
import android.database.Cursor
import android.provider.MediaStore
import javax.inject.Inject

class LocalStorageRepoImpl @Inject constructor(private val context: Context) :
    HomeContract.Repository {

    override fun getVideos(): List<String> {
        val videoItemHashSet: HashSet<String> = HashSet()
        val projection = arrayOf(
            MediaStore.Video.VideoColumns.DATA,
            MediaStore.Video.Media.DISPLAY_NAME
        )
        val cursor: Cursor? = context.contentResolver.query(
            MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
            projection, null, null, null
        )
        try {
            cursor?.let {
                it.moveToFirst()
                do {
                    videoItemHashSet.add(
                        it.getString(
                            it.getColumnIndexOrThrow(MediaStore.Video.Media.DATA)
                        )
                    )
                } while (it.moveToNext())
            }
        } catch (exception: Exception) {
            //TODO handle exception
            exception.printStackTrace()
        }
        cursor?.close()
        return ArrayList(videoItemHashSet)
    }
}