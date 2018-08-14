package com.ketan.musicbrainzapp.activities

import android.support.v7.app.AppCompatActivity
import com.ketan.musicbrainzapp.app.MusicBrainzApp
import retrofit2.Retrofit
import javax.inject.Inject

open class BaseActivity : AppCompatActivity() {

    @Inject
    lateinit var retrofit: Retrofit

    fun inject() {
        val foodiesHubApp = applicationContext as MusicBrainzApp
        foodiesHubApp.appDataComponent!!.inject(this)
    }

}