package com.dicoding.videosappfirebase


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.MediaController

import com.dicoding.videosappfirebase.databinding.ActivityDetailBinding
import java.util.*


class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    companion object{
        const val EXTRA_BRUH = "extra_bruh"
        const val EXTRA_VIDEO = "extra_video"
        const val EXTRA_DATE = "extra-date"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val nama = intent.getStringExtra(EXTRA_BRUH)
        val link = intent.getStringExtra(EXTRA_VIDEO)
        setDate()

        val mediaController = MediaController(this)
        binding.titleVideo.text = nama
        binding.video.setMediaController(mediaController)
        binding.video.setVideoPath(link)
        binding.video.requestFocus()
        binding.video.start()

    }

    private fun setDate() {
        var date = intent.getStringExtra(EXTRA_DATE)
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = date!!.toLong()
        val formattedDateTime = android.text.format.DateFormat.format("dd/MM/yyyy K:mm a",calendar).toString()
        date = formattedDateTime

        binding.time.text = date
    }



}