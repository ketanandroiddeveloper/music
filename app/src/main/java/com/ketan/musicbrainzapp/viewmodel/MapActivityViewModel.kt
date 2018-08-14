package com.ketan.musicbrainzapp.viewmodel

import android.arch.lifecycle.*
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import com.ketan.musicbrainzapp.entity.PlaceEntity
import com.ketan.musicbrainzapp.server.Request
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class MapActivityViewModel : ViewModel(),LifecycleObserver {

    var isVisible:Boolean = true

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart(){
        isVisible = true
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop(){
        isVisible = false
    }

    fun searchPlace(retrofit: Retrofit,placeName:String,progressBar: ProgressBar):MutableLiveData<PlaceEntity>{
        val data = MutableLiveData<PlaceEntity>()
        retrofit.create(Request::class.java).searchPlaces(placeName,"json").enqueue(object:Callback<PlaceEntity>{
            override fun onFailure(call: Call<PlaceEntity>?, t: Throwable?) {
                Log.e("searchPlace",t.toString())
                data.value = null
                progressBar.visibility = View.GONE
            }
            override fun onResponse(call: Call<PlaceEntity>?, response: Response<PlaceEntity>?) {
                if(!isVisible)return
                if(response!!.isSuccessful){
                    progressBar.visibility = View.GONE
                    data.value = response.body()
                }else{
                    progressBar.visibility = View.GONE
                    data.value = null
                }
            }
        })
        return data
    }

}