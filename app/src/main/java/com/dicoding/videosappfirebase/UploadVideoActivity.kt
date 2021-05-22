package com.dicoding.videosappfirebase

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.MediaController
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.dicoding.videosappfirebase.databinding.ActivityUploadVideoBinding
import com.dicoding.videosappfirebase.viewmodel.UploudVideoViewModel
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage

@Suppress("DEPRECATION")
class  UploadVideoActivity : AppCompatActivity() {
    private var videoUri: Uri? = null
    private lateinit var binding: ActivityUploadVideoBinding
    private var title: String = ""
    private var deepfake: Boolean? = null
    private lateinit var viewModel: UploudVideoViewModel

    private lateinit var progressDialog: ProgressDialog

    companion object {
        private const val VIDEO_GALLERY_CODE = 100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadVideoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Upload your videos"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.pickVideo.setOnClickListener {
            videoPickGallery()
        }

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please Wait")
        progressDialog.setMessage("Uploading video...")
        progressDialog.setCanceledOnTouchOutside(false)


        binding.uploadVideos.setOnClickListener {
            title = binding.EdtTitle.text.toString().trim()
            when {
                title.isEmpty() -> {
                    Toast.makeText(this, "Title is required", Toast.LENGTH_SHORT).show()
                }
                videoUri == null -> {
                    Toast.makeText(this, "Choose your videos", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    uploadVideotoFirebase()
                }
            }
        }
    }


    private fun uploadVideotoFirebase() {
        progressDialog.show()

        val timestamp = "" + System.currentTimeMillis()

        val filePathAndName = "Videos/video_$title"

        val storageReference = FirebaseStorage.getInstance().getReference(filePathAndName)
        storageReference.putFile(videoUri!!)
            .addOnSuccessListener {
                val uriTask = it.storage.downloadUrl
                while (!uriTask.isSuccessful);
                val downloadUri = uriTask.result
                if (uriTask.isSuccessful){
                    val hashMap = HashMap<String, Any>()
                    hashMap["id"] = "$timestamp"
                    hashMap["title"] = "$title"
                    hashMap["videoUrl"] = "$downloadUri"
                    hashMap["deepfake"] = "$deepfake"
                    hashMap["timestamp"] = "$timestamp"


                    val dbReference = FirebaseDatabase.getInstance("https://videosappfirebase-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Videos")
                    dbReference.child(title)
                        .setValue(hashMap)
                        .addOnSuccessListener {
                            progressDialog.dismiss()
                            Toast.makeText(this,"Video Uploaded", Toast.LENGTH_SHORT).show()
                        }
                        .addOnFailureListener {
                            progressDialog.dismiss()
                            Toast.makeText(this, "${it.message}", Toast.LENGTH_SHORT).show()
                        }
                }
            }
            .addOnFailureListener {
                progressDialog.dismiss()
                Toast.makeText(this, "${it.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun videoPickGallery() {
        val intent = Intent()
        intent.type = "video/*"
        intent.action = Intent.ACTION_GET_CONTENT

        startActivityForResult(
                Intent.createChooser(intent, "Choose video"),
                VIDEO_GALLERY_CODE)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (resultCode == RESULT_OK) {
            if (requestCode == VIDEO_GALLERY_CODE) {
                videoUri = data!!.data
                setVideoToVideoView()
            }
        } else {
            Toast.makeText(this, "Gagal ambil video", Toast.LENGTH_SHORT).show()
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun setVideoToVideoView() {
        val mediaController = MediaController(this)
        mediaController.setAnchorView(binding.videoView)

        binding.apply {
            videoView.setMediaController(mediaController)
            videoView.setVideoURI(videoUri)
            videoView.requestFocus()
            videoView.setOnPreparedListener {
                videoView.pause()
            }
        }
    }
}
