package com.github.teddynight.buscoming.ui

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import com.github.teddynight.buscoming.model.Bus
import com.github.teddynight.buscoming.model.Station
import com.github.teddynight.buscoming.network.BusApi
import com.github.teddynight.buscoming.network.BusApiStatus
import com.github.teddynight.buscoming.utlis.Location
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NearbyScreenViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
) : ViewModel() {
    val pos = MutableLiveData(Pair(0.0,0.0))
    private var _status = MutableLiveData(BusApiStatus.LOADING)
    val status: LiveData<BusApiStatus> = _status
    val stations: MutableLiveData<List<Station>> = MutableLiveData(emptyList())
    private val _sid = MutableLiveData("")
    val sid: LiveData<String> = _sid
    private val _stnStatus = MutableLiveData(false)
    val stnStatus:LiveData<Boolean> = _stnStatus
    val buses = MutableLiveData(emptyList<List<Bus>>())

//    init {
//        refresh()
//    }

    fun getLocation() {
        val location = Location(context).getLocation()
        pos.value = Pair(location.longitude,location.latitude)
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