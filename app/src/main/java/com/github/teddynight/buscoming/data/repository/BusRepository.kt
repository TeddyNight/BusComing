package com.github.teddynight.buscoming.data.repository

import androidx.lifecycle.MutableLiveData
import com.github.teddynight.buscoming.data.model.Bus
import com.github.teddynight.buscoming.data.model.Line
import com.github.teddynight.buscoming.network.BusApi

object BusRepository {
    val lId = MutableLiveData<String>(null)
    val bus = MutableLiveData<Bus?>(null)
    val direction = MutableLiveData<Boolean>(true)

    suspend fun refresh() {
        val direction = if (this.direction.value!!) 0 else 1
        if (lId.value != null) {
            bus.value = BusApi.retrofitService.getBusDetail(lId.value!!,direction)
        }
    }
}