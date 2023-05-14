package com.github.teddynight.buscoming.ui.nearby

import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.*
import com.github.teddynight.buscoming.data.model.Line
import com.github.teddynight.buscoming.data.repository.NearbyRepository
import com.github.teddynight.buscoming.data.repository.StationRepository
import com.github.teddynight.buscoming.utils.ReceiverLiveData
import com.github.teddynight.buscoming.utils.SensorManagerHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NearbyScreenViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
) : ViewModel() {
//    private val handler = Handler()
    val stations = NearbyRepository.stations
    val sid = StationRepository.sid
    val lines = StationRepository.lines
    private val sensorHelper = SensorManagerHelper(context)
    private var job = StationRepository.job
    val activeNetworkInfoLiveData = ReceiverLiveData(context, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)) { context, intent ->
        val manager = context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        manager.activeNetwork
    }

    init {
//        refresh()
        sensorHelper.setOnShakeListener(object : SensorManagerHelper.OnShakeListener {
            override fun onShake() {
                Log.d("Sensor","Shake")
                refresh()
            }
        })
    }

    fun getLocation() {
        NearbyRepository.getLocation(context)
    }

    fun getStn(sid: String) {
        if (activeNetworkInfoLiveData.value == null) {
            Toast.makeText(context,"网络不可用", Toast.LENGTH_SHORT).show()
        } else {
            job.cancelChildren()
            viewModelScope.launch(job) {
                try {
                    StationRepository.get(sid)
                } catch (e: Throwable) {
                    Log.e("NearbyScreen",e.message!!)
                    errorToast()
                }
            }
        }
    }

    fun refresh() {
        job.cancelChildren()
        viewModelScope.launch(job) {
            try {
                NearbyRepository.refresh()
                StationRepository.refresh()
            } catch (e: Throwable) {
                Log.e("NearbyScreen",e.message!!)
                errorToast()
            }
        }
    }

    private fun errorToast() {
        Toast.makeText(context,"加载失败", Toast.LENGTH_SHORT).show()
    }

    fun getWaitingTime(index: Int, direction: Boolean): String {
        val linePair = lines.value!![index]
        val line = if (direction) linePair[0] else linePair[1]
        var waitingTime = "--"
        if (line.arrivals.isNotEmpty()) {
            val time = line.arrivals.sorted()[0]
            val curTime = System.currentTimeMillis()
            if (time > curTime) waitingTime  = ((time - curTime) / (60 * 1000)).toString()
        }
        return waitingTime
    }
}