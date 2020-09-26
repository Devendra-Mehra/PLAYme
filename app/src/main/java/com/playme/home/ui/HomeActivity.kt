package com.playme.home.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.playme.R
import com.playme.core.BaseActivity
import com.playme.core.permissions.PermissionConstants
import com.playme.core.permissions.PermissionConstants.READ_EXTERNAL_STORAGE
import com.playme.core.permissions.PermissionConstants.getPermissions
import com.playme.core.permissions.PermissionsListener
import com.playme.extension.attachSnapHelperWithListener
import com.playme.extension.hide
import com.playme.extension.show
import com.playme.home.model.Video
import com.playme.home.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_home.*
import javax.inject.Inject


@AndroidEntryPoint
class HomeActivity : BaseActivity() {

    private val homeViewModel: HomeViewModel by viewModels()

    @Inject
    lateinit var pagerSnapHelper: PagerSnapHelper

    @Inject
    lateinit var mediaAdapter: MediaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        checkPermissionGrant()
        observe()
    }

    private fun checkPermissionGrant() {
        getPermissions(READ_EXTERNAL_STORAGE)?.let {
            if (isPermissionGranted(it, this)) {
                setUpRecyclerView()
            } else {
                requestPermissionsIfNotGranted(
                    permissions = it,
                    activity = this,
                    permissionsListener = object : PermissionsListener {
                        override fun onPermissionGranted() {
                            setUpRecyclerView()
                        }

                        override fun onRequestPermissionRationale() {
                            setError(
                                getString(R.string.permission_rationale_error),
                                R.drawable.ic_permission_denied
                            )
                        }

                        override fun onPermissionException(exceptionMessage: String) {
                            setError(exceptionMessage, R.drawable.ic_permission_denied)
                        }
                    },
                    requestCode = PermissionConstants.READ_EXTERNAL_STORAGE_REQUEST_CODE
                )
            }
        } ?: setError(
            getString(R.string.permission_operating_error),
            R.drawable.ic_permission_denied
        )
    }

    private fun observe() {
        homeViewModel.videos.observe(this, Observer {
            setData(it)
        })
        homeViewModel.error.observe(this, Observer {
            setError(it, R.drawable.ic_error)
        })
    }

    private fun setError(errorMessage: String, errorImage: Int) {
        rv_videos.hide()
        constraint_error_group.show()
        error_text.text = errorMessage
        error_image.setImageResource(errorImage)
    }

    private fun setData(videos: List<Video>) {
        mediaAdapter.setData(videos)
    }


    private fun setUpRecyclerView() {
        rv_videos.apply {
            layoutManager = LinearLayoutManager(this@HomeActivity)
            adapter = mediaAdapter
            attachSnapHelperWithListener(
                snapHelper = pagerSnapHelper,
                onSnapPositionChangeListener = {
                    Log.d("Log24", "onSnapPositionChangeListener $it")
                },
                onScrollDragging = {
                    Log.d("Log24", "onScrollDragging")
                },
                onScrollDraggingStopListener = { setlingPostion ->
                    Log.d("Log24", "onScrollDraggingStopListener $setlingPostion")
                })
        }
        setOnBookMarkClickedAction()
        homeViewModel.getVideos()
    }

    private fun setOnBookMarkClickedAction() {
        mediaAdapter.setOnBookMarkClickedAction { videoUrl, toRemoveBookMark ->
            if (toRemoveBookMark) {
                homeViewModel.storeBookMark(videoUrl)
            } else {
                homeViewModel.removeBookMark(videoUrl)
            }
        }
    }
}