package com.github.teddynight.buscoming.model

data class Nearby(val stations: List<Station>,
                  val buses: List<Bus>,
                  val arrivals: List<Arrival>)