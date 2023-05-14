package com.github.teddynight.buscoming.ui.bus

import android.util.Log
import androidx.lifecycle.*
import com.github.teddynight.buscoming.data.repository.BusRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BusScreenViewModel @Inject constructor(savedStateHandle: SavedStateHandle):
    ViewModel() {
    private val line0: String = checkNotNull(savedStateHandle["line0"])
    private val line1: String = checkNotNull(savedStateHandle["line1"])
    private val direction = BusRepository.direction
    private val job = Job()
    val bus = BusRepository.bus
    val arrivals: LiveData<List<String>?> = bus.map {
        if (it != null) {
            it!!.line.arrivals.sorted().map {
                val time = it
                val curTime = System.currentTimeMillis()
                if (time > curTime) {
                    ((time - curTime) / (1000*60)).toString()
                }
                else {
                    "--"
                }
            }
        }
        else {
            null
        }
    }

    init {
        BusRepository.lId.value = line0
        refresh()
    }

    fun changeDirection() {
        direction.value = !direction.value!!
        if (direction.value!!) {
            BusRepository.lId.value = line0
        }
        else {
            BusRepository.lId.value = line1
        }
        BusRepository.bus.value = null
        refresh()
    }

    fun refresh() {
        Log.d("BusScreen",BusRepository.lId.value!!)
        job.cancelChildren()
        viewModelScope.launch(job) {
            try {
                BusRepository.refresh()
            } catch(e: Throwable) {
                Log.e("BusScreen",e.message!!)
            }
        }
    }

}