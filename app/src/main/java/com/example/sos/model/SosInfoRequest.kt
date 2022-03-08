package com.example.sos.model

data class SosInfoRequest(
    val phoneNumbers:List<String>,
    val image:String,
    val location: Location
)