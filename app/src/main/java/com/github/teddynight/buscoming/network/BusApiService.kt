package com.github.teddynight.buscoming.network

import com.github.teddynight.buscoming.model.Bus
import com.github.teddynight.buscoming.model.Station
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL =
    "https://file.202016.xyz/"
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface BusApiService {
    @GET("busapi/nearby.php")
    suspend fun getNearby(@Query("lng") lng: Double, @Query("lat") lat:Double): List<Station>
    @GET("busapi/stndetail.php")
    suspend fun getStnDetail(@Query("sid") sId: String): List<List<Bus>>
}

object BusApi {
    val retrofitService: BusApiService by lazy { retrofit.create(BusApiService::class.java) }
}

enum class BusApiStatus { LOADING, ERROR, DONE }