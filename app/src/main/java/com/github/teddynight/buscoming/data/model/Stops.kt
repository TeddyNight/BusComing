package com.github.teddynight.buscoming.data.model

data class Stops(
   val busId: String,
   val list: HashMap<String, Bus>
)