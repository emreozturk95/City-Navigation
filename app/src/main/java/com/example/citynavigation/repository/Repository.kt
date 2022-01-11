package com.example.citynavigation.repository

import com.example.citynavigation.api.ApiService

class Repository(
    private val apiService: ApiService
) {
    suspend fun getCityById(id: String) = apiService.getCityById(id)
    suspend fun getCityList() = apiService.getCityList()
}