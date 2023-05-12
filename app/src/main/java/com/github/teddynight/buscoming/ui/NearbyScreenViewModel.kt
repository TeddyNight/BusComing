package com.github.teddynight.buscoming.ui

import android.app.Application
import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.core.content.getSystemService
import androidx.lifecycle.*
import com.github.teddynight.buscoming.model.Bus
import com.github.teddynight.buscoming.model.Station
import com.github.teddynight.buscoming.network.BusApi
import com.github.teddynight.buscoming.network.BusApiStatus
import com.github.teddynight.buscoming.repository.StnDetailRepository
import com.github.teddynight.buscoming.utlis.Location
import com.github.teddynight.buscoming.utlis.ReceiverLiveData
import com.github.teddynight.buscoming.utlis.SensorManagerHelper
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
    private val handler = Handler()
    val stnRepository = StnDetailRepository
    val pos = MutableLiveData(Pair(0.0,0.0))
    val stations: MutableLiveData<List<Station>?> = MutableLiveData(null)
    val sid = stnRepository.sid
    val buses = stnRepository.buses
    private val sensorHelper = SensorManagerHelper(context)
    private var job = Job()
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
        val location = Location(context).getLocation()
        pos.value = Pair(location.longitude,location.latitude)
    }

//    suspend fun getStn() {
//        buses.value = BusApi.retrofitService.getStnDetail(_sid.value!!)
//    }
    fun getStn(sid: String) {
        if (activeNetworkInfoLiveData.value == null) {
            Toast.makeText(context,"网络不可用", Toast.LENGTH_SHORT).show()
        } else {
            job.cancelChildren()
            viewModelScope.launch(job) {
                try {
                    stnRepository.get(sid)
                    handler.postDelayed(Runnable {
                        refreshStn() },15000)
                } catch (e: Throwable) {
                    errorToast()
                }
            }
        }
    }

    fun refreshStn() {
        if (activeNetworkInfoLiveData.value != null) {
            viewModelScope.launch(job) {
                try {
                    stnRepository.refresh()
                } catch (e: Throwable) {
                    errorToast()
                }
            }
        }
        handler.postDelayed(Runnable {
            refreshStn() },15000)
    }

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
                stations.value = null
                stations.value = BusApi.retrofitService.getNearby(
                    pos.value!!.first,
                    pos.value!!.second
                )
                stnRepository.refresh()
            } catch (e: Throwable) {
                errorToast()
            }
        }
    }

    fun errorToast() {
        Toast.makeText(context,"加载失败", Toast.LENGTH_SHORT).show()
    }
}