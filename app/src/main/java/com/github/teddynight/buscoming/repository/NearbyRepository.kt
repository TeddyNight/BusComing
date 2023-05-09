package com.github.teddynight.buscoming.repository

import androidx.lifecycle.*
import com.github.teddynight.buscoming.model.Bus
import com.github.teddynight.buscoming.model.Nearby
import com.github.teddynight.buscoming.model.Station
import com.github.teddynight.buscoming.network.BusApi
import com.github.teddynight.buscoming.network.BusApiStatus

class NearbyRepository {
    val pos = MutableLiveData(Pair(113.03f,23.15f))
    val status = MutableLiveData(BusApiStatus.LOADING)
    val nearby = MutableLiveData(Nearby(emptyList(), emptyMap()))

    suspend fun refreshAll() {
        nearby.value = BusApi.retrofitService.getNearby(pos.value!!.first,pos.value!!.second)
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