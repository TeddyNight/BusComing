package com.github.teddynight.buscoming.network

import com.github.teddynight.buscoming.model.Nearby
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

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
    @GET("busapi/nearby.php?lng=113.03&lat=23.15")
    suspend fun getNearby(): Nearby
}

object BusApi {
    val retrofitService: BusApiService by lazy { retrofit.create(BusApiService::class.java) }
}

enum class BusApiStatus { LOADING, ERROR, DONE }