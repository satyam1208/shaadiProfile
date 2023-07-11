package com.example.demoapplication

data class Location(
    val street: Street,
    val city: String,
    val state: String,
    val country: String,
    val postcode: Any?,
    val coordinates: Coordinates,
    val timezone: Timezone
)