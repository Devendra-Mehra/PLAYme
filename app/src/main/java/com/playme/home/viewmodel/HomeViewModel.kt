package com.playme.home.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.playme.home.model.HomeModel
import com.playme.home.model.Video

class HomeViewModel @ViewModelInject constructor(private val homeModel: HomeModel) : ViewModel() {

    private val _videos: MutableLiveData<List<Video>> by lazy {
        MutableLiveData<List<Video>>()
    }
    var videos: LiveData<List<Video>> = _videos

    private val _error: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    var error: LiveData<String> = _error


    fun getVideos() {
        val videos = homeModel.getVideos()
        if (videos.isNullOrEmpty()) {
            _error.postValue("No videos found on your phone")
        } else {
            _videos.postValue(videos)
        }
    }

    fun storeBookMark(videoUrl: String) {
        homeModel.storeBookMark(videoUrl)
    }

    fun removeBookMark(videoUrl: String) {
        homeModel.removeBookMark(videoUrl)
    }
}
