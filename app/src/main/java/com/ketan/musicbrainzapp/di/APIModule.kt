package com.ketan.musicbrainzapp.di

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.ketan.musicbrainzapp.BuildConfig
import com.ketan.musicbrainzapp.app.MusicBrainzApp
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Created by ketan on 4/13/2018.
 */
@Module
class APIModule constructor(var mainActivity: MusicBrainzApp) {


    fun provideHttpCash(application: MusicBrainzApp):Cache{
        val size =10*1024*1024
        return Cache(application.cacheDir, size.toLong())
    }
    fun provideOkHttpClient(cache: Cache):OkHttpClient{
        val client = OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.MINUTES)
        client.readTimeout(5,TimeUnit.MINUTES)
        client.writeTimeout(5,TimeUnit.MINUTES)
        client.cache(cache)
        return client.build()
    }
    @Provides
    @Singleton
    fun provideRetrofit():Retrofit{
        return  Retrofit.Builder()
                .addConverterFactory(ScalarsConverterFactory.create())
                .baseUrl(BuildConfig.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(provideOkHttpClient(provideHttpCash(mainActivity)))
                .build()
    }
}