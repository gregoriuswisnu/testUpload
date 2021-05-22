package com.dicoding.videosappfirebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.videosappfirebase.databinding.ActivityMainBinding
import com.dicoding.videosappfirebase.viewmodel.ListViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var video: ArrayList<ModelVideo>
    private lateinit var videoAdapter : MainAdapter
    private lateinit var viewModel: ListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Home"
        viewModel = ViewModelProvider(this,ViewModelProvider.NewInstanceFactory()).get(ListViewModel::class.java)


       binding.progressBar.visibility= View.VISIBLE
        setRv()

    }

    private fun setRv(){
        video = ArrayList()

        binding.rvVideo.layoutManager = LinearLayoutManager(applicationContext)
        videoAdapter = MainAdapter()
        viewModel.loadVideo()
        viewModel.getlist().observe(this,{User ->
            if (User!=null){
                videoAdapter.setData(User)

            }
        })

        binding.rvVideo.adapter = videoAdapter
        binding.progressBar.visibility= View.GONE


        videoAdapter.setOnItemClickCallback(object : MainAdapter.OnItemClickCallback{
            override fun onItemClicked(userItems: ModelVideo) {
                super.onItemClicked(userItems)
                Toast.makeText(this@MainActivity, "You pick ${userItems.title}", Toast.LENGTH_SHORT).show()
                val intent = Intent(this@MainActivity, DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_BRUH,userItems.title)
                intent.putExtra(DetailActivity.EXTRA_VIDEO,userItems.videoUrl)
                intent.putExtra(DetailActivity.EXTRA_DATE,userItems.timestamp)
                startActivity(intent)
            }
        })
        binding.uploadVideos.setOnClickListener {
            intent = Intent(this, UploadVideoActivity::class.java)
            startActivity(intent)
        }
    }



   /* fun loadVideo() {
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
                videoAdapter = MainAdapter(this@MainActivity, videoArrayList)
                binding.rvVideo.adapter = videoAdapter

                videoAdapter.setOnItemClickCallback(object : MainAdapter.OnItemClickCallback{
                    override fun onItemClicked(userItems: ModelVideo) {
                        super.onItemClicked(userItems)
                        Toast.makeText(this@MainActivity, "You pick ${userItems.title}", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@MainActivity, DetailActivity::class.java)
                        intent.putExtra(DetailActivity.EXTRA_BRUH,userItems.title)
                        intent.putExtra(DetailActivity.EXTRA_VIDEO,userItems.videoUrl)
                        intent.putExtra(DetailActivity.EXTRA_DATE,userItems.timestamp)
                        startActivity(intent)
                    }
                })

            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }
*/

}