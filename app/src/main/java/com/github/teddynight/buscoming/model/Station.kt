package com.github.teddynight.buscoming.model

import com.squareup.moshi.Json

data class Station(
    @Json(name="sId")
    val id: String,
    val name: String,
//    val buses: List<List<Bus>>
)