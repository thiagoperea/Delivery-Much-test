package com.thiagoperea.androidportfolio2021.presentation.repositorylist

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.thiagoperea.androidportfolio2021.R
import org.hamcrest.Matchers.allOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class RepositoryListActivityTest {

    @get:Rule
    var activityRule = ActivityScenarioRule(RepositoryListActivity::class.java)

    @Test
    fun testDoSearchFromKeyboard() {
        onView(withId(R.id.repositoryListSearchText))
            .perform(typeText("query"))

        onView(withId(R.id.repositoryListSearchText))
            .perform(pressImeActionButton())

        onView(withId(R.id.repositoryListView))
            .check(matches(hasDescendant(withText("Mock repository 1"))))
    }

    @Test
    fun testDoSearchFromSearchView() {
        onView(withId(R.id.repositoryListSearchText)).perform(typeText("query"))

        onView(
            allOf(withId(R.id.text_input_end_icon), withContentDescription("Pesquisar"))
        ).perform(click())

        onView(withId(R.id.repositoryListView))
            .check(matches(hasDescendant(withText("Mock repository 1"))))
    }
}