package com.github.teddynight.buscoming.model

data class Nearby(val stations: List<Station>,
                  val arrivals: Map<String,List<Long>>)