package com.igorsantos.listiningevents.domain.repository

import com.igorsantos.listiningevents.domain.model.Events
import com.igorsantos.listiningevents.domain.network.EventsService
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EventsRepository @Inject constructor(private val service: EventsService){

    suspend fun getEvents(): List<Events> {
        Timber.e("congrats")
        return service.events()
    }
}