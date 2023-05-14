package com.github.teddynight.buscoming.data.model

import com.squareup.moshi.Json

data class Bus(
    val line: Line,
    val list: List<Int>,
    val stops: List<String>,
)