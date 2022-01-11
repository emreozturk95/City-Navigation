package com.example.citynavigation.api

import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("/cities/{id}/")
    suspend fun getCityById(@Path("id") cityId: String): CityItem

    @GET("/cities")
    suspend fun getCityList(): City
}
