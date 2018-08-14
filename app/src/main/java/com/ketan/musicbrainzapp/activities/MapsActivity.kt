package com.ketan.musicbrainzapp.activities

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.SearchView
import android.util.Log
import android.view.View
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.ketan.musicbrainzapp.R
import com.ketan.musicbrainzapp.entity.PlacesItem
import com.ketan.musicbrainzapp.timerutility.Reminder
import com.ketan.musicbrainzapp.utility.AppUtility
import com.ketan.musicbrainzapp.viewmodel.MapActivityViewModel
import kotlinx.android.synthetic.main.activity_maps.*


//http://musicbrainz.org/ws/2/place/?query=chipping&fmt=json
class MapsActivity : BaseActivity(), OnMapReadyCallback ,LifecycleOwner{

    private lateinit var mMap: GoogleMap
    private lateinit var  viewModel:MapActivityViewModel
    private var places  : List<PlacesItem?>?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        inject()
        viewModel = ViewModelProviders.of(this).get(MapActivityViewModel::class.java)
        lifecycle.addObserver(viewModel)
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        searchPlace()
    }
    private fun searchPlace(){
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(place: String?): Boolean {
                if(place!=null && place.isNotEmpty()){
                    AppUtility.hideKeyboardFrom(this@MapsActivity,searchView)
                    progressBar.visibility = View.VISIBLE
                    viewModel.searchPlace(retrofit,place,progressBar).observe(this@MapsActivity, Observer {
                        if(it?.places != null && it.places!!.isNotEmpty()){
                            places = it.places!!
                            setMarker()
                        }
                    })
                    return true
                }else{
                    return false
                }
            }
            override fun onQueryTextChange(p0: String?): Boolean {
                Log.w("typing",p0)
                return true
            }

        })
    }
    private fun setMarker(){
        for(data:PlacesItem? in places!!){
            if(data!!.coordinates!=null){
                val latLng = LatLng(data.coordinates!!.latitude!!, data.coordinates!!.longitude!!)
                val marker =    mMap.addMarker(MarkerOptions().position(latLng).title(data.name))
                data.marker = marker
                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng))

                if(data.lifeSpan!=null && data.lifeSpan!!.begin!=null && data.lifeSpan!!.end!=null){
                   val time =  (data.lifeSpan!!.end!!.split("-")[0].toLong())-data.lifeSpan!!.begin!!.split("-")[0].toLong()
                    Reminder(time,marker,object :Reminder.OnFinishListener{
                        override fun onFinish(marker: Marker) {
                            runOnUiThread {
                                kotlin.run {
                                    marker.remove()
                                }
                            }
                        }

                    })
                }

            }
        }
    }



    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
    }
}
