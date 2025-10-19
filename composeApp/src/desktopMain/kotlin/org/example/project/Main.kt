package org.example.project

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.example.project.DATA.DI.commonModule
import org.koin.core.context.startKoin

fun main() = application {
    Window(onCloseRequest = ::exitApplication, title = "Self_Notron") {
        startKoin {
            modules(commonModule)
        }
        App() // This assumes your common App composable is accessible
    }
}
