package com.github.teddynight.buscoming.data.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.github.teddynight.buscoming.data.model.Station
import com.github.teddynight.buscoming.network.BusApi
import com.github.teddynight.buscoming.utils.Location

object NearbyRepository {
    val pos = MutableLiveData(Pair(0.0,0.0))
    val stations: MutableLiveData<List<Station>?> = MutableLiveData(null)

    fun getLocation(context: Context) {
        pos.value = Location.wgs84togcj02(Location(context).getLocation())
    }

    suspend fun refresh() {
        stations.value = null
        stations.value = BusApi.retrofitService.getNearby(
            pos.value!!.first,
            pos.value!!.second
        )
    }
}