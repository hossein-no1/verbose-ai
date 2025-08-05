package com.ai.verbose.core

import android.content.Context
import org.koin.dsl.module

fun androidPlatformModule(context: Context) = module {
    single<Context> { context.applicationContext }
}
