package com.github.teddynight.buscoming.ui
import android.util.Log
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.teddynight.buscoming.model.Arrival
import com.github.teddynight.buscoming.model.Bus
import com.github.teddynight.buscoming.model.Nearby
import com.github.teddynight.buscoming.model.Station
import com.github.teddynight.buscoming.network.BusApi
import com.github.teddynight.buscoming.network.BusApiStatus
import kotlinx.coroutines.launch

class StationListViewModel: ViewModel() {
    private val _status = MutableLiveData<BusApiStatus>()
    private val _stations = MutableLiveData<List<Station>>()
    private val _buses = MutableLiveData<List<Bus>>()
    private val _arrivals = MutableLiveData<List<Arrival>>()
    val status: LiveData<BusApiStatus> = _status
    val stations: LiveData<List<Station>> = _stations
    val buses: LiveData<List<Bus>> = _buses
    val arrivals: LiveData<List<Arrival>> = _arrivals


    init {
        loadingData()
    }

    private fun loadingData() {
        _status.value = BusApiStatus.LOADING
        viewModelScope.launch {
            try {
                val nearby = BusApi.retrofitService.getNearby()
                _stations.value = nearby.stations
                _status.value = BusApiStatus.DONE
            } catch (e: Exception) {
                Log.e("StationListViewModel", e.message!!)
                _status.value = BusApiStatus.ERROR
            }
        }
    }
}