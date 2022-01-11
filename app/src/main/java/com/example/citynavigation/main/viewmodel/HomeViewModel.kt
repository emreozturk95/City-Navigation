package com.example.citynavigation.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.citynavigation.api.City
import com.example.citynavigation.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Dispatcher
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    var city = MutableLiveData<City>()

    fun fetchCities() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.getCityList()
                city.postValue(response)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    }

}