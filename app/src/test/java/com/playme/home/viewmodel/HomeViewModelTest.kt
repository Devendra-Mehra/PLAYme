package com.playme.home.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockito_kotlin.*
import com.playme.home.model.HomeModelImpl
import com.playme.home.model.Video
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class HomeViewModelTest {

    private val homeModel: HomeModelImpl = mock()
    private val videoUrl = "video_url"
    private val homeViewModel: HomeViewModel by lazy {
        HomeViewModel(homeModel = homeModel)
    }

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()


    @Test
    fun `should update loading status when ask for get videos`() {
        val videoList: List<Video> = mutableListOf()
        val videoListCaptor = argumentCaptor<(List<Video>) -> Unit>()

        homeViewModel.getVideos()

        assertEquals(homeViewModel.loading.value, true)
        verify(homeModel).getVideos(videoListCaptor.capture())
        videoListCaptor.firstValue.invoke(videoList)
        assertEquals(homeViewModel.loading.value, false)
    }

    @Test
    fun `should return error when ask for get videos`() {
        val videoList: List<Video> = mutableListOf()
        val videoListCaptor = argumentCaptor<(List<Video>) -> Unit>()

        homeViewModel.getVideos()

        verify(homeModel).getVideos(videoListCaptor.capture())
        videoListCaptor.firstValue.invoke(videoList)
        assertEquals(homeViewModel.error.value, EMPTY_OR_NULL_LIST_ERROR)
    }

    @Test
    fun `should return video list when ask for get videos`() {
        val videoList: MutableList<Video> = mutableListOf()
        val video: Video = mock()
        videoList.add(video)
        val videoListCaptor = argumentCaptor<(List<Video>) -> Unit>()

        homeViewModel.getVideos()

        verify(homeModel).getVideos(videoListCaptor.capture())
        videoListCaptor.firstValue.invoke(videoList)
        assertEquals(homeViewModel.videos.value, videoList)
    }

    @Test
    fun `should call store bookmark when ask for store bookmark`() {
        homeViewModel.storeBookMark(videoUrl)

        verify(homeModel).storeBookMark(videoUrl)
    }

    @Test
    fun `should call remove bookmark when ask for remove bookmark`() {
        homeViewModel.removeBookMark(videoUrl)

        verify(homeModel).removeBookMark(videoUrl)
    }
}