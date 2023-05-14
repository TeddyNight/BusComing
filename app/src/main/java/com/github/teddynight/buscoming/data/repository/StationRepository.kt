package com.github.teddynight.buscoming.data.repository

import androidx.lifecycle.*
import com.github.teddynight.buscoming.data.model.Line
import com.github.teddynight.buscoming.network.BusApi
import kotlinx.coroutines.Job

object StationRepository {
    val sid = MutableLiveData<String>(null)
    val lines = MutableLiveData<List<List<Line>>?>(null)
    val job = Job()
    suspend fun get(sid: String) {
        StationRepository.sid.value = sid
        lines.postValue(null)
        refresh()
    }
    suspend fun refresh() {
        if (sid.value != null) {
            lines.postValue(BusApi.retrofitService.getStnDetail(sid.value!!))
        }
    }
}