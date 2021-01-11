package com.thiagoperea.deliverymuchtest.internal

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.thiagoperea.deliverymuchtest.repository.internal.InternalRepository
import org.koin.android.ext.android.get
import org.koin.android.ext.koin.androidContext
import org.koin.core.KoinComponent
import org.koin.core.context.startKoin

class DeliveryMuchTestApplication : Application(), KoinComponent {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@DeliveryMuchTestApplication)
            modules(
                viewModelModule,
                repositoryModule,
                dataSourceModule,
                databaseModule
            )
        }

        val isDayMode = get<InternalRepository>().doInitialSetup(this)
        setDayNightMode(isDayMode)
    }

    private fun setDayNightMode(isDayMode: Boolean) {
        if (isDayMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
    }
}