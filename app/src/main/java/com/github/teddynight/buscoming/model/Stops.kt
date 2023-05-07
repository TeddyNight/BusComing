package com.github.teddynight.buscoming.model

data class Stops(
   val busId: String,
   val list: HashMap<String,Bus>
)