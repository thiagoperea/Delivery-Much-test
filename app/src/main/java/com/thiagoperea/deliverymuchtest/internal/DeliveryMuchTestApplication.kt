package com.thiagoperea.deliverymuchtest.internal

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class DeliveryMuchTestApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@DeliveryMuchTestApplication)
            modules(koinModule)
        }
    }
}