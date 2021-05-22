package com.dicoding.videosappfirebase

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.MediaController
import android.widget.TextView
import android.widget.Toast
import android.widget.VideoView
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.videosappfirebase.databinding.ActivityDetailBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    companion object{
        const val EXTRA_BRUH = "extra_bruh"
        const val EXTRA_VIDEO = "extra_video"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val nama = intent.getStringExtra(EXTRA_BRUH)
        val link = intent.getStringExtra(EXTRA_VIDEO)
        val mediaController = MediaController(this)

        binding.titleVideo.text = nama
        binding.video.setMediaController(mediaController)
        binding.video.setVideoPath(link)
        binding.video.requestFocus()
        binding.video.start()





    }
}