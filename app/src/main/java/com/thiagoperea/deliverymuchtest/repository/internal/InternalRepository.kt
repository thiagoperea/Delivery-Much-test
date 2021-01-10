package com.thiagoperea.deliverymuchtest.repository.internal

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO

class InternalRepository(
    private val preferences: SharedPreferences
) {

    companion object {
        const val IS_DAY_MODE = "is.day.mode"
    }

    fun doInitialSetup() {
        if (!preferences.contains(IS_DAY_MODE)) {
            val isDayModeOnSetup = AppCompatDelegate.getDefaultNightMode() == MODE_NIGHT_NO
            preferences.edit().putBoolean(IS_DAY_MODE, isDayModeOnSetup).apply()
        }
    }

    fun switchDayNightTheme(): Boolean {
        var isDayMode = preferences.getBoolean(IS_DAY_MODE, false)
        isDayMode = !isDayMode
        preferences.edit().putBoolean(IS_DAY_MODE, isDayMode).apply()
        return isDayMode
    }
}
