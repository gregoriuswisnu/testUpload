package com.dicoding.videosappfirebase

class ModelVideo {

    var id: String? = null
    var title: String? = null
    var timestamp: String? = null
    var videoUrl: String? = null


    constructor(){

    }

    constructor(id: String?, title: String?, timestamp: String?, videoUri: String?) {
        this.id = id
        this.title = title
        this.timestamp = timestamp
        this.videoUrl = videoUri
    }


}