package com.thiagoperea.androidportfolio2021.repository.internal

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration

class InternalRepository(
    private val preferences: SharedPreferences
) {

    companion object {
        const val IS_DAY_MODE = "is.day.mode"
    }

    fun doInitialSetup(appContext: Context): Boolean {
        if (!preferences.contains(IS_DAY_MODE)) {
            val isDayModeOnSetup =
                appContext.resources.configuration.uiMode.and(Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_NO
            preferences.edit().putBoolean(IS_DAY_MODE, isDayModeOnSetup).apply()
            return isDayModeOnSetup
        } else {
            return preferences.getBoolean(IS_DAY_MODE, false)
        }
    }

    fun switchDayNightTheme(): Boolean {
        var isDayMode = preferences.getBoolean(IS_DAY_MODE, false)
        isDayMode = !isDayMode
        preferences.edit().putBoolean(IS_DAY_MODE, isDayMode).apply()
        return isDayMode
    }
}
