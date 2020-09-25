package com.playme.home.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.playme.home.model.HomeModel

class HomeViewModel @ViewModelInject constructor(homeModel: HomeModel) : ViewModel() {
}