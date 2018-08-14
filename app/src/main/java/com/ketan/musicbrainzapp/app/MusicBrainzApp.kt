package com.ketan.musicbrainzapp.app

import android.app.Application
import com.foodieshub.component.AppComponent
import com.foodieshub.component.DaggerAppComponent
import com.ketan.musicbrainzapp.di.APIModule

class MusicBrainzApp : Application() {

    var appDataComponent : AppComponent?=null

    override fun onCreate() {
        super.onCreate()

    appDataComponent = DaggerAppComponent.builder().aPIModule(APIModule(this)).build()
    appDataComponent?.inject(this)
     /*   appDataComponent =   DaggerAppComponent.builder()
                .aPIModule(APIModule(this))
                .build()
        appDataComponent!!.inject(this)*/
    }

}