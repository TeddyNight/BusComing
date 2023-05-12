package com.github.teddynight.buscoming.repository

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.github.teddynight.buscoming.model.Station
import com.github.teddynight.buscoming.network.BusApi
import com.github.teddynight.buscoming.utlis.Location

object NearbyRepository {
    val pos = MutableLiveData(Pair(0.0,0.0))
    val stations: MutableLiveData<List<Station>?> = MutableLiveData(null)

    fun getLocation(context: Context) {
        val location = Location(context).getLocation()
        pos.value = Pair(location.longitude,location.latitude)
    }

    suspend fun refresh() {
        stations.value = null
        stations.value = BusApi.retrofitService.getNearby(
            pos.value!!.first,
            pos.value!!.second
        )
    }
}