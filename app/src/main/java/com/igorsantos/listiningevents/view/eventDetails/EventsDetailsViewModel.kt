package com.igorsantos.listiningevents.view.eventDetails

import androidx.lifecycle.*
import com.igorsantos.listiningevents.domain.model.Events
import com.igorsantos.listiningevents.domain.repository.EventsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*
import javax.inject.Inject

@HiltViewModel
class EventsDetailsViewModel @Inject constructor(
    private val repository: EventsRepository
) : ViewModel() {

    private var _eventDetails = MutableLiveData<Events>()
    val eventDetails: LiveData<Events> = _eventDetails

    private var _loading = MutableLiveData(true)
    val loading: LiveData<Boolean> = _loading

    private var _errorLoad = MutableLiveData<String>()
    val errorLoad: LiveData<String> = _errorLoad

    fun getEventDetails(id: String) = viewModelScope.launch {
        try {
            val result = repository.getEventDetails(id)
            _eventDetails.value = result
            _loading.value = false
        } catch (e: Exception) {
            _errorLoad.value = e.toString()
            _loading.value = false
        }
    }

    fun setDetails(): String {
        val data = _eventDetails.value ?: return ""
        val date = LocalDateTime.ofInstant(Instant.ofEpochMilli(data.date), ZoneId.of("UTC"))
        return "${data.title}\n\n" +
            "${date.format(DateTimeFormatter.ofPattern("EEE dd/MMMM/yyyy", Locale("pt","BR")))}\n\n" +
            ("R$ " + data.price.toString().replace(".", ",")) +
            "\n\n${data.description}\n\n"
    }
}