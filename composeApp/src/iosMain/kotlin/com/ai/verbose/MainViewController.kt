package com.ai.verbose

import androidx.compose.ui.window.ComposeUIViewController
import com.ai.verbose.core.di.initKoin
import com.ai.verbose.ui.App

fun MainViewController() = ComposeUIViewController(
    configure = { initKoin() },
    content = { App() }
)