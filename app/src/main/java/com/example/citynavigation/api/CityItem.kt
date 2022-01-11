package com.example.citynavigation.api

import com.google.gson.annotations.SerializedName

data class CityItem(
    @SerializedName("created")
    val created: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("populations")
    val populations: List<Population>
)