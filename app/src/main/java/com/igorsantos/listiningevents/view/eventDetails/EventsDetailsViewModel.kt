package com.igorsantos.listiningevents.view.eventDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.igorsantos.listiningevents.arq.lifecycle.Event
import com.igorsantos.listiningevents.domain.model.Events
import com.igorsantos.listiningevents.domain.repository.EventsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class EventsDetailsViewModel @Inject constructor(
    private val repository: EventsRepository
) : ViewModel() {

    private var _eventDetails = MutableLiveData<Events>()
    val eventDetails: LiveData<Events> = _eventDetails

    private var _onEventsDetailsClicked = MutableLiveData<Event<Events>>()
    val onEventsDetailsClicked: LiveData<Event<Events>> = _onEventsDetailsClicked

    suspend fun getEventDetails(id: String): Events {
        val result = repository.getEventDetails(id)
        _eventDetails.value = result
        return result
    }

    fun onEventsDetails(data: Events) {
        _onEventsDetailsClicked.value = Event(data)
    }

    fun setDetails(): String {
        val data = _eventDetails.value ?: return ""
        val date = LocalDateTime.ofInstant(Instant.ofEpochMilli(data.date), ZoneId.of("UTC"))
        return "${data.title}\n\n" +
            "${date.format(DateTimeFormatter.ofPattern("EEE, dd/MMMM/yyyy"))}\n\n" +
            ("R$ " + data.price.toString().replace(".", ",")) +
            "\n\n${data.description}\n\n"
    }
}