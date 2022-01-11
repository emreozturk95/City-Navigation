package com.example.citynavigation.main.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.citynavigation.api.CityItem
import com.example.citynavigation.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val repository: Repository) :
    ViewModel() {
    var id = MutableLiveData<String>()
    var cityDetail = MutableLiveData<CityItem>()

    fun fetchCityDetails() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = id.value?.let { repository.getCityById(it) }
                cityDetail.postValue(response)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    }


}