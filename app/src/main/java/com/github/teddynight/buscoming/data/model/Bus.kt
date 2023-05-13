package com.github.teddynight.buscoming.data.model

import com.squareup.moshi.Json

data class Bus(
    @Json(name = "bId")
    val id: String,
    val name: String,
    val startSn: String,
    val endSn: String,
    val price: String,
    // TODO: use date if needed
    val firstTime: String,
    val lastTime: String,
    val arrivals: List<Long>
)