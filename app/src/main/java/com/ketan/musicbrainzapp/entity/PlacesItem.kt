package com.ketan.musicbrainzapp.entity

import com.google.android.gms.maps.model.Marker
import com.google.gson.annotations.SerializedName

data class PlacesItem(
	var area: Area? = null,
	var score: Int? = null,
	@SerializedName("life-span")
	var lifeSpan: LifeSpan? = null,
	var typeId: String? = null,
	var name: String? = null,
	var disambiguation: String? = null,
	var id: String? = null,
	var type: String? = null,
	var address: String? = null,
	var coordinates: Coordinates? = null,
	var marker:Marker?=null
)
