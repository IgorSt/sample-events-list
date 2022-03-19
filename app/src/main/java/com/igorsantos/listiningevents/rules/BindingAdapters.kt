package com.igorsantos.listiningevents.rules

import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.BindingAdapter
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

@BindingAdapter("formatCurrency")
fun AppCompatTextView.formatCurrency(value: Double) {
    val text = "R$ " + value.toString().replace(".", ",")

    setText(text)
}

@BindingAdapter("formatDate")
fun AppCompatTextView.formatDate(value: Long) {
    val date = LocalDateTime.ofInstant(Instant.ofEpochMilli(value), ZoneId.of("UTC"))

    text = (date.format(DateTimeFormatter.ofPattern("EEE dd/MMMM/yyyy", Locale("pt","BR"))))
}

@BindingAdapter("app:loading")
fun loading(view: View, value: Boolean) {
    view.visibility = if (value) View.VISIBLE else View.GONE
}