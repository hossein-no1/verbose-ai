package com.ai.verbose

import android.app.Application
import com.ai.verbose.core.androidPlatformModule
import com.ai.verbose.core.di.initKoin

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin(
            externalModule = androidPlatformModule(this.applicationContext)
        )
    }
}