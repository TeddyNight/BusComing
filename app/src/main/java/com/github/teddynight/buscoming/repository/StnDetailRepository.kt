package com.github.teddynight.buscoming.repository

import android.util.Log
import androidx.lifecycle.*
import com.github.teddynight.buscoming.model.Bus
import com.github.teddynight.buscoming.network.BusApi
import kotlinx.coroutines.*

object StnDetailRepository {
    val sid = MutableLiveData<String>(null)
    val buses = MutableLiveData<List<List<Bus>>>(null)
    suspend fun get(sid: String) {
        this.sid.value = sid
        this.buses.value = null
        refresh()
    }
    suspend fun refresh() {
        if (sid.value != null) {
            buses.value = BusApi.retrofitService.getStnDetail(this.sid.value!!)
        }
    }
}