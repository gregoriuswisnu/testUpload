package com.dicoding.videosappfirebase.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.videosappfirebase.ModelVideo
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ListViewModel: ViewModel() {
    var videoArrayList= MutableLiveData<ArrayList<ModelVideo>>()

    fun loadVideo() {
        val list = ArrayList<ModelVideo>()

        val ref = FirebaseDatabase.getInstance("https://videosappfirebase-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Videos")
        ref.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                list.clear()
                for (ds in snapshot.children) {
                    val modelVideo = ds.getValue(ModelVideo::class.java)
                    list.add(modelVideo!!)
                }
                videoArrayList.postValue(list)
            }
            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    fun getlist() : LiveData<ArrayList<ModelVideo>>{
        return videoArrayList
    }

}