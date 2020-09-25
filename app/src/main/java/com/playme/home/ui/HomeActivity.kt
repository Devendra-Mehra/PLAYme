package com.playme.home.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.playme.R
import com.playme.core.BaseActivity
import com.playme.extension.attachSnapHelperWithListener
import com.playme.extension.hide
import com.playme.extension.show
import com.playme.home.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_home.*
import javax.inject.Inject


@AndroidEntryPoint
class HomeActivity : BaseActivity() {

    private val homeViewModel: HomeViewModel by viewModels()

    @Inject
    lateinit var pagerSnapHelper: PagerSnapHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setUpRecyclerView()
        observe()
    }


    private fun observe() {
        homeViewModel.videos.observe(this, Observer {

        })
        homeViewModel.error.observe(this, Observer {
            setError(it)
        })
    }

    private fun setError(errorMessage: String) {
        rv_videos.hide()
        constraint_error_group.show()
        error_text.text = errorMessage
    }


    private fun setUpRecyclerView() {
        rv_videos.apply {
            layoutManager = LinearLayoutManager(this@HomeActivity)
            attachSnapHelperWithListener(pagerSnapHelper) { }
        }
    }
}