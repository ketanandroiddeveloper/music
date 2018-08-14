package com.ketan.musicbrainzapp.server

import com.ketan.musicbrainzapp.entity.PlaceEntity
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Request {

    @GET("place")
    fun searchPlaces(@Query("query")placeName:String,@Query("fmt")formate:String):Call<PlaceEntity>

}