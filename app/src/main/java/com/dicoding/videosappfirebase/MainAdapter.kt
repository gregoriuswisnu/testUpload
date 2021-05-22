package com.dicoding.videosappfirebase

import android.content.Context
import android.media.AudioDeviceCallback
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
import com.dicoding.videosappfirebase.databinding.ItemVideoBinding

class MainAdapter:RecyclerView.Adapter<MainAdapter.HolderVideo>() {
    var videoArrayList=ArrayList<ModelVideo>()
    private var onItemClickCallback: OnItemClickCallback? = null

    fun setData(item : ArrayList<ModelVideo>){
        videoArrayList?.clear()
        videoArrayList?.addAll(item)
        notifyDataSetChanged()
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }


    inner class HolderVideo(itemView:View): RecyclerView.ViewHolder(itemView){
        private val binding = ItemVideoBinding.bind(itemView)

        fun bind(modelVideo: ModelVideo){
            with(itemView){
                binding.titleVideo.text = modelVideo.title
                val timestamp = modelVideo.timestamp.also { this@HolderVideo.binding.time.text = it }

                val calendar = Calendar.getInstance()
                calendar.timeInMillis = timestamp!!.toLong()
                val formattedDateTime = android.text.format.DateFormat.format("dd/MM/yyyy K:mm a",calendar).toString()
/*                val mediaController = MediaController(context)*/


                binding.time.text = formattedDateTime

                itemView.setOnClickListener { onItemClickCallback?.onItemClicked(modelVideo) }
            }

        }
    }

    interface OnItemClickCallback{
        fun onItemClicked(userItems: ModelVideo){

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderVideo {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_video,parent,false)
        return HolderVideo(view)
    }

    override fun onBindViewHolder(holder: HolderVideo, position: Int) {
        holder.bind(videoArrayList[position])
    }



    /*private fun setVideoUrl(modelVideo: ModelVideo, holder: HolderVideo) {
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
                    holder.progressBar.visibility = View.GONE
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
            mediaPlayer.stop()
        }
    }
*/
    override fun getItemCount(): Int {
        return videoArrayList!!.size
    }
}