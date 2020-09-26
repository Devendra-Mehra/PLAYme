package com.playme.home.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Video(
    var videoUrl: String,
    var isBookmark: Boolean = false
) : Parcelable