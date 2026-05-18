package com.movilesuniandes.vinilos.features.e2e

import android.os.SystemClock
import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.PerformException
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.movilesuniandes.vinilos.MainActivity
import com.movilesuniandes.vinilos.R
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class CreateAlbumInvalidE2ETest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun crear_album_invalido_muestra_errores_en_ui() {
        // Ir a pestaña Crear
        onView(allOf(withText("Crear"), isDisplayed())).perform(click())

        // Esperar formulario
        onView(isRoot()).perform(waitForView(withId(R.id.inputName), 5000))

        // Dejar campos vacíos y pulsar crear
        onView(withId(R.id.inputName)).perform(click())
        onView(withId(R.id.btnCreateAlbum)).perform(click())

        // Esperar y verificar que aparecen mensajes de error (campo requerido)
        onView(withText("El nombre es obligatorio"))
            .check(matches(isDisplayed()))
    }

    private fun waitForView(viewMatcher: Matcher<View>, timeoutMs: Long): ViewAction {
        return object : ViewAction {
            override fun getDescription(): String {
                return "wait up to $timeoutMs ms for view matching $viewMatcher"
            }

            override fun getConstraints(): Matcher<View> = isRoot()

            override fun perform(uiController: UiController, view: View) {
                val endTime = SystemClock.uptimeMillis() + timeoutMs
                do {
                    if (findMatchingView(view, viewMatcher)) return
                    uiController.loopMainThreadForAtLeast(50)
                } while (SystemClock.uptimeMillis() < endTime)

                throw PerformException.Builder()
                    .withActionDescription(description)
                    .withCause(AssertionError("View not found within $timeoutMs ms"))
                    .build()
            }
        }
    }

    private fun findMatchingView(root: View, matcher: Matcher<View>): Boolean {
        if (matcher.matches(root) && allOf(isDisplayed()).matches(root)) {
            return true
        }

        if (root !is android.view.ViewGroup) return false
        for (i in 0 until root.childCount) {
            if (findMatchingView(root.getChildAt(i), matcher)) {
                return true
            }
        }
        return false
    }
}
