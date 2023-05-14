package com.github.teddynight.buscoming.data.model

import com.squareup.moshi.Json

data class Line(
    @Json(name = "lId")
    val id: String,
    val name: String,
    val startSn: String,
    val endSn: String,
    val price: String,
    val firstTime: String,
    val lastTime: String,
    val arrivals: List<Long>
)