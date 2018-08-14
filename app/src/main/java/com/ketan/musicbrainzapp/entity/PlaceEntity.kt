package com.ketan.musicbrainzapp.entity

data class PlaceEntity(
	var places: List<PlacesItem?>? = null,
	var offset: Int? = null,
	var created: String? = null,
	var count: Int? = null
)
