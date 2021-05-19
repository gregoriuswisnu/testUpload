package com.dicoding.videosappfirebase

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.VideoView
import androidx.recyclerview.widget.RecyclerView
import java.util.*
import kotlin.collections.ArrayList

class MainAdapter(private var context:Context, private var videoArrayList:ArrayList<ModelVideo>?):RecyclerView.Adapter<MainAdapter.HolderVideo>() {

    class HolderVideo(itemView:View): RecyclerView.ViewHolder(itemView){

        var videoView:VideoView = itemView.findViewById(R.id.video)
        var title: TextView = itemView.findViewById(R.id.title_video)
        var time: TextView = itemView.findViewById(R.id.time)
        var progressBar: ProgressBar = itemView.findViewById(R.id.progressBar)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderVideo {
        val view = LayoutInflater.from(context).inflate(R.layout.item_video,parent,false)
        return HolderVideo(view)
    }

    override fun onBindViewHolder(holder: HolderVideo, position: Int) {
        val modelVideo = videoArrayList!![position]
        val id:String? = modelVideo.id
        val title:String? = modelVideo.title
        val timestamp:String? = modelVideo.timestamp
        val video:String? = modelVideo.videoUrl

        val calendar = Calendar.getInstance()
            calendar.timeInMillis = timestamp!!.toLong()
            val formattedDateTime = android.text.format.DateFormat.format("dd/MM/yyyy K:mm a",calendar).toString()

            holder.title.text = title
            holder.time.text = formattedDateTime
            setVideoUrl(modelVideo,holder)



    }

    private fun setVideoUrl(modelVideo: ModelVideo, holder: HolderVideo) {
        holder.progressBar.visibility = View.VISIBLE

        val videoUrl: String? = modelVideo.videoUrl

        val mediaController = MediaController(context)
        mediaController.setAnchorView(holder.videoView)
        val videoUri = Uri.parse(videoUrl)

        holder.videoView.setMediaController(mediaController)
        holder.videoView.setVideoURI(videoUri)
        holder.videoView.requestFocus()

        holder.videoView.setOnPreparedListener{mediaPlayer ->
            mediaPlayer.start()
        }
        holder.videoView.setOnInfoListener(MediaPlayer.OnInfoListener{mp, what, extra ->
            when(what){
                MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START ->{
                    holder.progressBar.visibility = View.VISIBLE
                    return@OnInfoListener true
                }
                MediaPlayer.MEDIA_INFO_BUFFERING_START ->{
                    holder.progressBar.visibility = View.VISIBLE
                    return@OnInfoListener true
                }
                MediaPlayer.MEDIA_INFO_BUFFERING_END ->{
                    holder.progressBar.visibility = View.GONE
                    return@OnInfoListener true
                }
            }
            false
        })
        holder.videoView.setOnCompletionListener { mediaPlayer ->
            mediaPlayer.start()
        }
    }

    override fun getItemCount(): Int {
        return videoArrayList!!.size
    }
}