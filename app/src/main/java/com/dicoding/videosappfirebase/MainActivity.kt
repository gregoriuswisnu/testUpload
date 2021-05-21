package com.dicoding.videosappfirebase

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.videosappfirebase.databinding.ActivityMainBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var videoArrayList: ArrayList<ModelVideo>
    private lateinit var videoAdapter : MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Home"


        binding.uploadVideos.setOnClickListener {
            intent = Intent(this, UploadVideoActivity::class.java)
            startActivity(intent)
        }

        loadVideo()
    }

    fun loadVideo() {
        videoArrayList = ArrayList()

        val ref = FirebaseDatabase.getInstance("https://videosappfirebase-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Videos")
        ref.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                videoArrayList.clear()
                for (ds in snapshot.children){
                    val modelVideo = ds.getValue(ModelVideo::class.java)
                    videoArrayList.add(modelVideo!!)
                }
                binding.rvVideo.layoutManager = LinearLayoutManager(applicationContext)
                /*binding.rvVideo.setHasFixedSize(true)*/
                videoAdapter = MainAdapter(this@MainActivity,videoArrayList)
                binding.rvVideo.adapter = videoAdapter

                videoAdapter.setOnItemClickCallback(object : MainAdapter.OnItemClickCallback{
                    override fun onItemClicked(userItems: ModelVideo) {
                        super.onItemClicked(userItems)
                        Toast.makeText(this@MainActivity, "You pick ${userItems.title}", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@MainActivity, DetailActivity::class.java)
/*                        intent.putExtra(DetailActivity.EXTRA_BRUH,userItems.videoUrl)*/
                        intent.putExtra(DetailActivity.EXTRA_BRUH,userItems.title)
/*                        intent.putExtra(DetailActivity.EXTRA_BRUH,userItems.timestamp)*/
                        startActivity(intent)
                    }
                })

            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }


}