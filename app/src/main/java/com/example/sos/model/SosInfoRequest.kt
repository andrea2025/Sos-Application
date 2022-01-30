package com.example.sos.model

data class SosInfoRequest(
    val phoneNumbers:List<String>,
    val image:String,
    val location: GeoCordinate
)

data class GeoCordinate(
   val longitude:String,
   val latitude:String
)
