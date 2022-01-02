package com.igorsantos.listiningevents.domain.network

import com.igorsantos.listiningevents.domain.model.Checkin
import com.igorsantos.listiningevents.domain.model.Events
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface EventsService {

    @GET("events")
    suspend fun events(): List<Events>

    @GET("events/{id}")
    suspend fun detailsEvent(@Path("id") id: String): Events

    @POST("checkin")
    suspend fun checkin(@Body data: Checkin): Any
}