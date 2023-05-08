package com.github.teddynight.buscoming.model

data class Nearby(val stations: List<Station>,
                  val arrivals: List<Arrival>)