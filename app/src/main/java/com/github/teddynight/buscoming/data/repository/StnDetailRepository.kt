package com.github.teddynight.buscoming.data.repository

import androidx.lifecycle.*
import com.github.teddynight.buscoming.data.model.Bus
import com.github.teddynight.buscoming.network.BusApi
import kotlinx.coroutines.Job

object StnDetailRepository {
    val sid = MutableLiveData<String>(null)
    val buses = MutableLiveData<List<List<Bus>>?>(null)
    val job = Job()
    suspend fun get(sid: String) {
        StnDetailRepository.sid.value = sid
        buses.postValue(null)
        refresh()
    }
    suspend fun refresh() {
        if (sid.value != null) {
            buses.postValue(BusApi.retrofitService.getStnDetail(sid.value!!))
        }
    }
}