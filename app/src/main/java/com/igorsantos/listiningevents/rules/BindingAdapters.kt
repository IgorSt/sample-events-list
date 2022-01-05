package com.igorsantos.listiningevents.rules

import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.BindingAdapter
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@BindingAdapter("formatCurrency")
fun AppCompatTextView.formatCurrency(value: Double) {
    val text = "R$ " + value.toString().replace(".", ",")

    setText(text)
}

@BindingAdapter("formatDate")
fun AppCompatTextView.formatDate(value: Long) {
    val date = LocalDateTime.ofInstant(Instant.ofEpochMilli(value), ZoneId.of("UTC"))

    text = (date.format(DateTimeFormatter.ofPattern("EEE, dd/MMMM/yyyy")))
}