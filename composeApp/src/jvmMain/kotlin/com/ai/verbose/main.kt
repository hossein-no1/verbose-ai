package com.ai.verbose

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.ai.verbose.core.di.initKoin
import com.ai.verbose.ui.App

fun main() {
    initKoin()
    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "Verbose",
        ) {
            App()
        }
    }
}