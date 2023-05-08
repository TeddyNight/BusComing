package com.github.teddynight.buscoming.ui
import android.util.Log
import androidx.lifecycle.*
import com.github.teddynight.buscoming.model.Station
import com.github.teddynight.buscoming.network.BusApi
import com.github.teddynight.buscoming.network.BusApiStatus
import com.github.teddynight.buscoming.repository.NearbyRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class StationListViewModel: ViewModel() {
    private var repository = NearbyRepository.getInstance()
    var stations = repository.stations
}