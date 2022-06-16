package com.elnaz.theguardiannewsfeed.utils

import android.app.Activity
import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import android.widget.Toast

fun Context.toast(msg: String, duration: Int = Toast.LENGTH_SHORT): Toast {
    return Toast.makeText(this, msg, duration).apply { show() }
}

fun Activity.isNetworkAvailable(): Boolean {
    val cm = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
    return cm.activeNetworkInfo != null
}