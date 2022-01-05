package com.igorsantos.listiningevents.arq.lifecycle

import androidx.lifecycle.Observer

/**
 * Data holder para o valor sendo emitido pelo LiveData
 */
open class Event<out T>(private val content: T) {
    private var hasBeenHandled = false

    fun getIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

    fun peek(): T = content

    fun markHandled() {
        hasBeenHandled = true
    }
}

/**
 * Um observer que lê o valor do Event UMA UNICA VEZ
 */
class EventObserver<T>(private val onEventUnhandled: (T) -> Unit) : Observer<Event<T>> {
    override fun onChanged(event: Event<T>?) {
        event?.getIfNotHandled()?.let { value ->
            onEventUnhandled(value)
        }
    }
}