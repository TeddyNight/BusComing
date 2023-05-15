package com.github.teddynight.buscoming.utils

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import java.lang.Math.PI

/*
Downloaded from github.com/Kavya-24/Location-Finder
wgs84togcj02 function taken from github.com/geosmart/coordtransform
*/

class Location(private val context: Context) {
    lateinit var locationManager: LocationManager
    private var locationGPS: Location? = null
    private var locationNetwork: Location? = null

    var latGPS: Double = 0.0
    var longGPS: Double = 0.0
    var latNetwork: Double = 0.0
    var longNetwork: Double = 0.0

//    init {
//        latGPS = getLocation().latitude
//        longGPS = getLocation().longitude
//        Log.e("CLs", "In init")
//    }

    @SuppressLint("MissingPermission")
    //Returns a location object to be used
    fun getLocation(): Location {

        locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0F, object :
            LocationListener {
            override fun onLocationChanged(location: Location) {
                locationNetwork = location
            }
        })

        //Check for the last location
        val localNetworkLocation =
            locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
        if (localNetworkLocation != null) {
            locationNetwork = localNetworkLocation
            latNetwork = locationNetwork!!.latitude
            longNetwork = locationNetwork!!.longitude

        }

        val localGpsLocation =
            locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        if (localGpsLocation != null) {
            locationGPS = localGpsLocation
            latGPS = locationGPS!!.latitude
            longGPS = locationGPS!!.latitude
        }


        if ((locationGPS != null && locationNetwork == null) || locationGPS!!.accuracy >= locationNetwork!!.accuracy) {
            return locationGPS!!
        } else {
            return locationNetwork!!
        }
    }

    companion object {
        private val a: Double = 6378245.0
        private val ee: Double = 0.00669342162296594323

        fun wgs84togcj02(location: Location): Pair<Double,Double> {
            val lng = location.longitude
            val lat = location.latitude
            var dlat: Double = transformlat(lng - 105.0, lat - 35.0)
            var dlng: Double = transformlng(lng - 105.0, lat - 35.0)
            val radlat: Double = lat / 180.0 * PI
            var magic = Math.sin(radlat)
            magic = 1 - ee * magic * magic
            val sqrtmagic = Math.sqrt(magic)
            dlat = dlat * 180.0 / (a * (1 - ee) / (magic * sqrtmagic) * PI)
            dlng = dlng * 180.0 / (a / sqrtmagic * Math.cos(radlat) * PI)
            val mglat = lat + dlat
            val mglng = lng + dlng
            return Pair(mglng,mglat)
        }

        fun transformlat(lng: Double, lat: Double): Double {
            var ret =
                -100.0 + 2.0 * lng + 3.0 * lat + 0.2 * lat * lat + 0.1 * lng * lat + 0.2 * Math.sqrt(
                    Math.abs(lng)
                )
            ret += (20.0 * Math.sin(6.0 * lng * PI) + 20.0 * Math.sin(2.0 * lng * PI)) * 2.0 / 3.0
            ret += (20.0 * Math.sin(lat * PI) + 40.0 * Math.sin(lat / 3.0 * PI)) * 2.0 / 3.0
            ret += (160.0 * Math.sin(lat / 12.0 * PI) + 320 * Math.sin(lat * PI / 30.0)) * 2.0 / 3.0
            return ret
        }

        fun transformlng(lng: Double, lat: Double): Double {
            var ret = 300.0 + lng + 2.0 * lat + 0.1 * lng * lng + 0.1 * lng * lat + 0.1 * Math.sqrt(
                Math.abs(lng)
            )
            ret += (20.0 * Math.sin(6.0 * lng * PI) + 20.0 * Math.sin(2.0 * lng * PI)) * 2.0 / 3.0
            ret += (20.0 * Math.sin(lng * PI) + 40.0 * Math.sin(lng / 3.0 * PI)) * 2.0 / 3.0
            ret += (150.0 * Math.sin(lng / 12.0 * PI) + 300.0 * Math.sin(lng / 30.0 * PI)) * 2.0 / 3.0
            return ret
        }
    }
}