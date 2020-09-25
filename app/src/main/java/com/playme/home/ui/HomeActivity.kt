package com.playme.home.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.playme.R
import com.playme.extension.hide
import com.playme.extension.show
import com.playme.home.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_home.*


@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        observe()
    }


    private fun observe() {
        homeViewModel.videos.observe(this, Observer {

        })
        homeViewModel.error.observe(this, Observer {

        })
    }

    private fun setError() {
        rv_videos.hide()
        constraint_error_group.show()
    }


    private fun setUpRecyclerView() {

    }
}