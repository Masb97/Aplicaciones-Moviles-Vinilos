package com.movilesuniandes.vinilos.features.e2e

import android.os.SystemClock
import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.PerformException
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import org.hamcrest.CoreMatchers.containsString
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
import com.movilesuniandes.vinilos.core.testing.hasTextInputLayoutErrorText

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

        // Ensure fields are empty and press create
        onView(withId(R.id.inputName)).perform(androidx.test.espresso.action.ViewActions.replaceText(""), androidx.test.espresso.action.ViewActions.closeSoftKeyboard())
        onView(withId(R.id.btnCreateAlbum)).perform(androidx.test.espresso.action.ViewActions.scrollTo(), click())

        // Esperar a que exista el contenedor de errores, luego desplazarse y verificar texto
            onView(isRoot()).perform(waitForView(withId(R.id.inputNameLayout), 5000))
            onView(withId(R.id.inputNameLayout))
                .check(matches(hasTextInputLayoutErrorText("El nombre")))
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

                throw AssertionError("View not found within $timeoutMs ms")
            }
        }
    }

    private fun findMatchingView(root: View, matcher: Matcher<View>): Boolean {
        if (matcher.matches(root)) {
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
