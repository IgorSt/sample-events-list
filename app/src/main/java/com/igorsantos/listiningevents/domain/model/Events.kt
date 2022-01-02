package com.igorsantos.listiningevents.domain.model

import java.time.LocalDateTime

data class Events(
    val date: Long,
    val description: String,
    val image: String,
    val longitude: Double,
    val latitude: Double,
    val price: Double,
    val title: String,
    val id: String
)
