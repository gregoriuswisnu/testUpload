package com.dicoding.videosappfirebase

import android.os.Parcelable


data class VideoEntity(
    var id: String,
    var title: String,
    var videoUrl: String,
    var deepFake: Boolean
)
