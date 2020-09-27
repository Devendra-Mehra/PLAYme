package com.playme.home.model

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import com.playme.core.PersistenceContract
import junit.framework.Assert.assertFalse
import junit.framework.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@RunWith(MockitoJUnitRunner::class)
class HomeModelImplTest {

    private val videoUrl1 = "video_url_one"
    private val videoUrl2 = "video_url_two"
    private val videoUrl3 = "video_url_three"
    private val videoUrl = "video_url"
    private val repository: HomeContract.Repository = mock()
    private val persistence: PersistenceContract = mock()
    private lateinit var executorService: ExecutorService

    private val homeModelImpl: HomeModelImpl by lazy {
        HomeModelImpl(
            repository = repository,
            persistence = persistence,
            executorService = executorService
        )
    }

    @Before
    fun setUp() {
        executorService = Executors.newFixedThreadPool(2)
    }

    @Test
    fun `should call get bookmark and get video list when ask for get videos`() {
        val stringList: List<String> = mock()
        whenever(repository.getVideos()).thenReturn(stringList)

        homeModelImpl.getVideos {}

        verify(persistence).getBookMarked()
    }

    @Test
    fun `should call store bookmark when ask for store bookmark`() {
        homeModelImpl.storeBookMark(videoUrl)

        verify(persistence).storeBookMark(videoUrl)
    }

    @Test
    fun `should call remove bookmark when ask for remove bookmark`() {
        homeModelImpl.removeBookMark(videoUrl)

        verify(persistence).removeBookMark(videoUrl)
    }

    @Test
    fun `should return video list when ask for get videos`() {
        val videoUrlList: List<String> = getVideoList()
        val bookmarkUrl: Set<String> = getBookMarked()

        whenever(repository.getVideos()).thenReturn(videoUrlList)
        whenever(persistence.getBookMarked()).thenReturn(bookmarkUrl)

        homeModelImpl.getVideos { videos ->
            videos.forEach {
                assertTrue(videoUrlList.contains(it.videoUrl))
            }
        }
    }

    @Test
    fun `should mark bookmark as true or false when bookmark url's contains video url's`() {
        val videoUrlList: List<String> = getVideoList()
        val bookmarkUrl: Set<String> = getBookMarked()

        whenever(repository.getVideos()).thenReturn(videoUrlList)
        whenever(persistence.getBookMarked()).thenReturn(bookmarkUrl)

        homeModelImpl.getVideos { videos ->
            videos.forEach {
                if (bookmarkUrl.contains(it.videoUrl)) {
                    assertTrue(it.isBookmark)
                } else {
                    assertFalse(it.isBookmark)
                }
            }
        }
    }

    @Test
    fun `should mark bookmark as false when bookmark url list is null`() {
        whenever(repository.getVideos()).thenReturn(getVideoList())
        whenever(persistence.getBookMarked()).thenReturn(null)

        homeModelImpl.getVideos { videos ->
            videos.forEach {
                assertFalse(it.isBookmark)
            }
        }
    }

    @Test
    fun `should mark bookmark as false when bookmark url list is empty`() {
        val bookmarkUrl: Set<String> = mutableSetOf()

        whenever(repository.getVideos()).thenReturn(getVideoList())
        whenever(persistence.getBookMarked()).thenReturn(bookmarkUrl)

        homeModelImpl.getVideos { videos ->
            videos.forEach {
                assertFalse(it.isBookmark)
            }
        }
    }

    private fun getVideoList(): List<String> {
        return mutableListOf(videoUrl1, videoUrl2, videoUrl3)
    }

    private fun getBookMarked(): Set<String> {
        return mutableSetOf(videoUrl2)
    }

}