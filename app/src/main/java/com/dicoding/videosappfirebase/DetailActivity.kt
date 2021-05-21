package com.dicoding.videosappfirebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.VideoView
import com.dicoding.videosappfirebase.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    companion object{
        const val EXTRA_BRUH = "extra_bruh"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val title :TextView = findViewById(R.id.title_video)
        val nama = intent.getStringExtra(EXTRA_BRUH)
        val vid : VideoView = findViewById(R.id.video)
        //val dptvid = intent(EXTRA_BRUH)

        title.text = nama


    }
}