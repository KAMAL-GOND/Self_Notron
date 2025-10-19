package org.example.project.SCREEN.Widgets

actual fun Logd(tag: String, message: String) {
    println("$tag: $message")
}
//expect fun Loge(tag: String, message: String)

