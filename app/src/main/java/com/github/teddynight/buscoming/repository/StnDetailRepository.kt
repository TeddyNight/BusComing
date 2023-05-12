package com.github.teddynight.buscoming.repository

import android.util.Log
import androidx.lifecycle.*
import com.github.teddynight.buscoming.model.Bus
import com.github.teddynight.buscoming.network.BusApi

object StnDetailRepository {
    val sid = MutableLiveData("")
    val buses = MutableLiveData<List<List<Bus>>>(null)
    suspend fun get(sid: String) {
        this.sid.value = sid
        try {
            this.buses.value = null
            refresh()
        } catch (e: Throwable) {
            Log.e("StnDetailRepository",e.message!!)
        }
    }
    suspend fun refresh() {
        buses.value = BusApi.retrofitService.getStnDetail(this.sid.value!!)
    }
}