package com.github.teddynight.buscoming.ui

import android.util.Log
import androidx.lifecycle.*
import com.github.teddynight.buscoming.model.Bus
import com.github.teddynight.buscoming.model.Station
import com.github.teddynight.buscoming.network.BusApi
import com.github.teddynight.buscoming.network.BusApiStatus
import kotlinx.coroutines.launch

class NearbyScreenViewModel: ViewModel() {
    val pos = MutableLiveData(Pair(113.03f,23.15f))
    private var _status = MutableLiveData(BusApiStatus.LOADING)
    val status: LiveData<BusApiStatus> = _status
    val stations: MutableLiveData<List<Station>> = MutableLiveData(emptyList())
    private val _sid = MutableLiveData("")
    val sid: LiveData<String> = _sid
    private val _stnStatus = MutableLiveData(false)
    val stnStatus:LiveData<Boolean> = _stnStatus
    val buses = MutableLiveData(emptyList<List<Bus>>())

    init {
        refresh()
    }

    suspend fun getStn() {
        buses.value = BusApi.retrofitService.getStnDetail(_sid.value!!)
    }

    fun refreshStn(sid: String) {
        viewModelScope.launch {
            try {
                _stnStatus.value = false
                _sid.value = sid
                getStn()
                _stnStatus.value = true
            } catch (e: Throwable) {
                Log.e("StationCart",e.message!!)
            }
        }
    }

    fun refresh() {
        viewModelScope.launch {
            try {
                _status.value = BusApiStatus.LOADING
                stations.value = BusApi.retrofitService.getNearby(
                    pos.value!!.first,
                    pos.value!!.second)
                _status.value = BusApiStatus.DONE
            } catch (e: Throwable) {
                _status.value = BusApiStatus.ERROR
                Log.e("NearbyScreen",e.message!!)
            }
        }
    }
}