package com.example.sos.model

data class SosInfoResponse(
    val `data`: Data,
    val message: String,
    val status: String
)

data class Data(
    val id: Int,
    val image: Any?,
    val location: Location,
    val phoneNumbers: List<String>
)

data class Location(
    val latitude: String,
    val longitude: String
)