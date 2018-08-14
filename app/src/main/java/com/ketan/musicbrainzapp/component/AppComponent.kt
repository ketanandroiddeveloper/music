package com.foodieshub.component

import com.ketan.musicbrainzapp.activities.BaseActivity
import com.ketan.musicbrainzapp.app.MusicBrainzApp
import com.ketan.musicbrainzapp.di.APIModule
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton


@Singleton
@Component(modules = arrayOf(AndroidInjectionModule::class, APIModule::class))
interface AppComponent {
    fun inject(baseActivity: BaseActivity)
    fun inject(baseActivity: MusicBrainzApp)
}