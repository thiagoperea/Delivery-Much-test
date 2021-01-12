package com.thiagoperea.androidportfolio2021.repository.internal

import android.content.SharedPreferences
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.assertFalse
import org.junit.Test

class InternalRepositoryTest {

    @Test
    fun `doInitialSetup should execute if key does not exists`() {
        //arrange
        val preferencesMock = mockk<SharedPreferences>(relaxed = true)
        val preferencesEditorMock = mockk<SharedPreferences.Editor>(relaxed = true)

        val repository = InternalRepository(preferencesMock)

        every { preferencesMock.contains("is.day.mode") } returns false
        every { preferencesMock.edit() } returns preferencesEditorMock

        //act
        repository.doInitialSetup(mockk(relaxed = true))

        //assert
        verify(exactly = 1) {
            preferencesEditorMock.putBoolean(eq("is.day.mode"), any())
        }
    }

    @Test
    fun `doInitialSetup should not execute if key exists`() {
        //arrange
        val preferencesMock = mockk<SharedPreferences>(relaxed = true)
        val preferencesEditorMock = mockk<SharedPreferences.Editor>(relaxed = true)

        val repository = InternalRepository(preferencesMock)

        every { preferencesMock.contains("is.day.mode") } returns true
        every { preferencesMock.edit() } returns preferencesEditorMock

        //act
        repository.doInitialSetup(mockk(relaxed = true))

        //assert
        verify(exactly = 0) {
            preferencesEditorMock.putBoolean(eq("is.day.mode"), any())
        }
    }

    @Test
    fun `switchDayNight should save inverse key`() {
        //arrange
        val preferencesMock = mockk<SharedPreferences>(relaxed = true)
        val preferencesEditorMock = mockk<SharedPreferences.Editor>(relaxed = true)

        val repository = InternalRepository(preferencesMock)

        every { preferencesMock.getBoolean("is.day.mode", any()) } returns true
        every { preferencesMock.edit() } returns preferencesEditorMock

        //act
        val currentMode = repository.switchDayNightTheme()

        //assert
        verify(exactly = 1) {
            preferencesEditorMock.putBoolean(eq("is.day.mode"), eq(false))
        }
        assertFalse(currentMode)
    }
}