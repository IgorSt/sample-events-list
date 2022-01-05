package com.igorsantos.listiningevents.view.events

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.igorsantos.listiningevents.arq.lifecycle.Event
import com.igorsantos.listiningevents.domain.model.Events
import com.igorsantos.listiningevents.domain.repository.EventsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class EventsViewModel @Inject constructor(
    private val repository: EventsRepository
) : ViewModel() {

    private var _eventsList = MutableLiveData<List<Events>>()
    val eventsList: LiveData<List<Events>> = _eventsList

    private var _onEventsDetailsClicked = MutableLiveData<Event<Events>>()
    val onEventsDetailsClicked: LiveData<Event<Events>> = _onEventsDetailsClicked

    suspend fun getEvents(): List<Events> {
        val result = repository.getEvents()
        _eventsList.value = result
        return result
    }

    fun onEventsDetails(data: Events) {
        _onEventsDetailsClicked.value = Event(data)
    }
}