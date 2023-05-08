package com.github.teddynight.buscoming.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.teddynight.buscoming.model.Bus
import com.github.teddynight.buscoming.network.BusApiStatus
import com.github.teddynight.buscoming.repository.NearbyRepository
import kotlinx.coroutines.launch

class NearbyScreenViewModel: ViewModel() {
    private var repository = NearbyRepository.getInstance()
    private var _status = MutableLiveData(BusApiStatus.LOADING)
    val status: LiveData<BusApiStatus> = _status
    init {
        viewModelScope.launch {
            try {
                repository.refresh()
                _status.value = BusApiStatus.DONE
            } catch (e: Throwable) {
                _status.value = BusApiStatus.ERROR
                Log.e("NearbyScreen",e.message!!)
            }
        }
    }
}