package com.igorsantos.listiningevents.domain.repository

import com.igorsantos.listiningevents.domain.model.Checkin
import com.igorsantos.listiningevents.domain.model.Events
import com.igorsantos.listiningevents.domain.network.EventsService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EventsRepository @Inject constructor(private val service: EventsService){

    suspend fun getEvents(): List<Events> {
        Timber.e("congrats list of events")
        return service.events()
    }

    suspend fun getEventDetails(id: String): Events {
        Timber.e("congrats event by id")
        return service.detailsEvent(id)
    }

    suspend fun setCheckin(id: String, name: String, email: String) = withContext(Dispatchers.IO) {
        try {
            Timber.e("congrats made checkin")
            val data = Checkin(id, name, email)
            service.checkin(data)
            true
        } catch (e: Exception) {
            false
        }
    }
}