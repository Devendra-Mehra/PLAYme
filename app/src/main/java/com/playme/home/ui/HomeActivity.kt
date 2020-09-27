package com.playme.home.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.playme.R
import com.playme.core.BaseActivity
import com.playme.core.permissions.PermissionConstants
import com.playme.core.permissions.PermissionConstants.STORAGE
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
        getPermissions(STORAGE)?.let {
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
            rv_videos.show()
            setData(it)
        })
        homeViewModel.loading.observe(this, Observer {
            if (it) {
                constraint_progress_group.show()
            } else {
                constraint_progress_group.hide()
            }
        })
        homeViewModel.error.observe(this, Observer {
            setError(it, R.drawable.ic_error)
        })
    }

    private fun setError(errorMessage: String, errorImage: Int) {
        rv_videos.hide()
        constraint_progress_group.hide()
        constraint_error_group.show()
        error_text.text = errorMessage
        error_image.setImageResource(errorImage)
    }

    private fun setData(videos: List<Video>) {
        mediaAdapter.setData(videos)
        mediaAdapter.notifyDataSetChanged()
    }


    private fun setUpRecyclerView() {
        rv_videos.apply {
            layoutManager = LinearLayoutManager(this@HomeActivity)
            adapter = mediaAdapter
            attachSnapHelperWithListener(
                snapHelper = pagerSnapHelper,
                onSnapPositionChangeListener = { position, isScrolledUpEnabled ->
                    if (isScrolledUpEnabled) {
                        mediaAdapter.pausePlayer(position + 1)
                    } else {
                        mediaAdapter.pausePlayer(position - 1)
                    }
                })
        }
        setOnBookMarkClickedAction()
        setOnPlayerViewErrorAction()
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

    private fun setOnPlayerViewErrorAction() {
        mediaAdapter.setOnPlayerViewErrorAction { adapterPosition ->
            rv_videos.scrollToPosition(adapterPosition)
        }
    }
}