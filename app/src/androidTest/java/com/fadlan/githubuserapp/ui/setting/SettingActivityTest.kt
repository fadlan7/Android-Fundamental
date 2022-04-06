package com.fadlan.githubuserapp.ui.setting

import android.view.View
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed
import androidx.test.espresso.matcher.ViewMatchers.isRoot
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.fadlan.githubuserapp.R
import junit.framework.TestCase
import kotlinx.coroutines.delay
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.regex.Matcher


@RunWith(AndroidJUnit4ClassRunner::class)
class SettingActivityTest {

    @Before
    fun setup() {
        ActivityScenario.launch(SettingActivity::class.java)
    }

    @Test
     fun testChangeTheme() {
        onView(ViewMatchers.withId(R.id.switch_theme))
            .check(matches(isCompletelyDisplayed()))
            .perform(click())
    }
}