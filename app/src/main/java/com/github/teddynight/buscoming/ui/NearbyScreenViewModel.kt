package com.github.teddynight.buscoming.ui

import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.*
import com.github.teddynight.buscoming.data.repository.NearbyRepository
import com.github.teddynight.buscoming.data.repository.StnDetailRepository
import com.github.teddynight.buscoming.utils.ReceiverLiveData
import com.github.teddynight.buscoming.utils.SensorManagerHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NearbyScreenViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
) : ViewModel() {
//    private val handler = Handler()
    private val nearbyRepository = NearbyRepository
    private val stnRepository = StnDetailRepository
    val stations = nearbyRepository.stations
    val sid = stnRepository.sid
    val buses = stnRepository.buses
    private val sensorHelper = SensorManagerHelper(context)
    private var job = stnRepository.job
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
        nearbyRepository.getLocation(context)
    }

    fun getStn(sid: String) {
        if (activeNetworkInfoLiveData.value == null) {
            Toast.makeText(context,"网络不可用", Toast.LENGTH_SHORT).show()
        } else {
            job.cancelChildren()
            viewModelScope.launch(job) {
                try {
                    stnRepository.get(sid)
                } catch (e: Throwable) {
                    errorToast()
                }
            }
        }
    }

//    fun refreshStn() {
//        if (activeNetworkInfoLiveData.value != null) {
//            viewModelScope.launch(job) {
//                try {
//                    stnRepository.refresh()
//                } catch (e: Throwable) {
//                    errorToast()
//                }
//            }
//        }
//        handler.postDelayed(Runnable {
//            refreshStn() },15000)
//    }

//    fun refreshStn(sid: String) {
//        viewModelScope.launch {
//            try {
//                _stnStatus.value = false
//                _sid.value = sid
//                getStn()
//                _stnStatus.value = true
//            } catch (e: Throwable) {
//                Log.e("StationCart",e.message!!)
//            }
//        }
//    }

    fun refresh() {
        job.cancelChildren()
        viewModelScope.launch(job) {
            try {
                nearbyRepository.refresh()
                stnRepository.refresh()
            } catch (e: Throwable) {
                errorToast()
            }
        }
    }

    private fun errorToast() {
        Toast.makeText(context,"加载失败", Toast.LENGTH_SHORT).show()
    }
}