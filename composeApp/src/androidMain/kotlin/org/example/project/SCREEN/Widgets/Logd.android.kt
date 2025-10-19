package org.example.project.SCREEN.Widgets

import android.util.Log

actual fun Logd(tag: String, message: String) {
    Log.d(tag, message)
}