package com.igorsantos.listiningevents.view.events

import android.util.Log
import androidx.lifecycle.*
import com.igorsantos.listiningevents.arq.lifecycle.Event
import com.igorsantos.listiningevents.domain.model.Events
import com.igorsantos.listiningevents.domain.repository.EventsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventsViewModel @Inject constructor(
    private val repository: EventsRepository
) : ViewModel(), DefaultLifecycleObserver {

    private var _eventsList = MutableLiveData<List<Events>>()
    val eventsList: LiveData<List<Events>> = _eventsList

    private var _errorLoad = MutableLiveData<String>()
    val errorLoad: LiveData<String> = _errorLoad

    private var _onEventsDetailsClicked = MutableLiveData<Event<Events>>()
    val onEventsDetailsClicked: LiveData<Event<Events>> = _onEventsDetailsClicked

    private var _loading = MutableLiveData(true)
    val loading: LiveData<Boolean> = _loading

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        getEvents()
    }

    private fun getEvents() = viewModelScope.launch {
        try {
            val result = repository.getEvents()
            _eventsList.value = result
            _loading.value = false
        }catch (e: Exception) {
            Log.d("jdfhdfh", "getEvents: $e")
            _errorLoad.value = e.toString()
            _loading.value = false
        }
    }

    fun onEventsDetails(data: Events) {
        _onEventsDetailsClicked.value = Event(data)
    }
}