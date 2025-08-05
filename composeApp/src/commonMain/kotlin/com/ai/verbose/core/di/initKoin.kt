package com.ai.verbose.core.di

import org.koin.core.context.startKoin
import org.koin.core.module.Module

fun initKoin(externalModule: Module? = null) {
    startKoin {
        externalModule?.let {
            modules(it)
        }
        modules(
            appModule
        )
    }
}