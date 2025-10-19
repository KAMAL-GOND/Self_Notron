package org.example.project

import androidx.compose.ui.window.ComposeUIViewController
import org.example.project.DATA.DI.commonModule
import org.koin.core.context.startKoin

fun MainViewController() = ComposeUIViewController {
    startKoin {
        modules(commonModule)
    }
    App() }