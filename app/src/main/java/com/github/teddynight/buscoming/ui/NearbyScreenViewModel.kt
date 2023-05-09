package com.github.teddynight.buscoming.ui

import android.util.Log
import androidx.lifecycle.*
import com.github.teddynight.buscoming.network.BusApiStatus
import com.github.teddynight.buscoming.repository.NearbyRepository
import kotlinx.coroutines.launch

class NearbyScreenViewModel: ViewModel() {
    private var repository = NearbyRepository.getInstance()
    private var _status = MutableLiveData(BusApiStatus.LOADING)
    val status: LiveData<BusApiStatus> = _status
    val stations = repository.nearby.map {
        it.stations
    }
    val arrivals = repository.nearby.map {
        it.arrivals
    }

    init {
        refresh()
    }
    fun refresh() {
        viewModelScope.launch {
            try {
                _status.value = BusApiStatus.LOADING
                repository.refreshAll()
                _status.value = BusApiStatus.DONE
            } catch (e: Throwable) {
                _status.value = BusApiStatus.ERROR
                Log.e("NearbyScreen",e.message!!)
            }
        }
    }
}