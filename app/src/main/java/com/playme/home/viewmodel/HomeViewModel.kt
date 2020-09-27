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

    private val _loading: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }
    var loading: LiveData<Boolean> = _loading

    private val _error: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    var error: LiveData<String> = _error

    fun getVideos() {
        _loading.postValue(true)
        homeModel.getVideos { videos ->
            _loading.postValue(false)
            if (videos.isNullOrEmpty()) {
                _error.postValue(EMPTY_OR_NULL_LIST_ERROR)
            } else {
                _videos.postValue(videos)
            }
        }
    }

    fun storeBookMark(videoUrl: String) {
        homeModel.storeBookMark(videoUrl)
    }

    fun removeBookMark(videoUrl: String) {
        homeModel.removeBookMark(videoUrl)
    }

    override fun onCleared() {
        homeModel.clearResource()
        super.onCleared()
    }
}
