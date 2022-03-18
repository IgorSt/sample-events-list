package com.igorsantos.listiningevents.view.checkin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.igorsantos.listiningevents.domain.repository.EventsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class DialogCheckinViewModel @Inject constructor(
    private val repository: EventsRepository
): ViewModel() {

    private var _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading

    lateinit var _eventId: String
    lateinit var _name: String
    lateinit var _email: String


    fun checkin(){
        viewModelScope.launch {
            val id = _eventId
            val name = _name
            val email = _email
            try {
                repository.setCheckin(id, name, email)
                _loading.value = true
            } catch (e: Exception) {
                _loading.value = false
            }
        }
    }

    fun setup(id: String, name: String, email: String){
        _eventId = id
        _name = name
        _email = email

        checkin()
    }
}