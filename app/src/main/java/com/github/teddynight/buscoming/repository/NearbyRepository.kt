package com.github.teddynight.buscoming.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.teddynight.buscoming.model.Nearby
import com.github.teddynight.buscoming.model.Station
import com.github.teddynight.buscoming.network.BusApi

class NearbyRepository {
    val pos = MutableLiveData(Pair(113.03f,23.15f))
    private val _stations = MutableLiveData(emptyList<Station>())
    val stations: LiveData<List<Station>> = _stations

    suspend fun refresh() {
        val nearby = BusApi.retrofitService.getNearby(pos.value!!.first,pos.value!!.second)
        _stations.value = nearby.stations
    }
    
    companion object {
        // For Singleton instantiation
        @Volatile private var instance: NearbyRepository? = null

        fun getInstance() =
            instance ?: synchronized(this) {
                instance ?: NearbyRepository().also { instance = it }
            }
    }
}