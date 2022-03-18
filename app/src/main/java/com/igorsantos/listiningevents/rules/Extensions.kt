package com.igorsantos.listiningevents.rules

import android.text.TextUtils
import android.util.Patterns

fun String.emailValidator(): Boolean {
    return !TextUtils.isEmpty(this) && Patterns.EMAIL_ADDRESS.matcher(this).matches()
}