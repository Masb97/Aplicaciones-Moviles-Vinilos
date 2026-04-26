package com.movilesuniandes.vinilos

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import org.hamcrest.Matchers.allOf
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun toolbar_es_visible() {
        onView(withId(R.id.toolbar))
            .check(matches(isDisplayed()))
    }

    @Test
    fun bottom_nav_es_visible() {
        onView(withId(R.id.bottom_nav))
            .check(matches(isDisplayed()))
    }

    @Test
    fun toolbar_muestra_titulo_vinilos() {
        onView(withText("Vinilos"))
            .check(matches(isDisplayed()))
    }

    @Test
    fun tab_albumes_es_visible() {
        onView(allOf(withText("Álbumes"), isDisplayed()))
            .check(matches(isDisplayed()))
    }

    @Test
    fun tab_artistas_es_visible() {
        onView(allOf(withText("Artistas"), isDisplayed()))
            .check(matches(isDisplayed()))
    }

    @Test
    fun tab_coleccionistas_es_visible() {
        onView(allOf(withText("Coleccionistas"), isDisplayed()))
            .check(matches(isDisplayed()))
    }
}
