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
class ArtistDetailE2ETest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun navegar_al_detalle_de_artista_muestra_informacion() {
        // Navegar a la pestaña de artistas
        onView(allOf(withText("Artistas"), isDisplayed())).perform(click())

        // Esperar a que cargue el listado
        onView(isRoot()).perform(waitForView(withId(R.id.recyclerViewArtists), 12000))

        // Clic en el primer artista (usualmente Rubén Blades si viene del backend real)
        onView(withText("Rubén Blades Bellido de Luna")).perform(click())

        // Verificar que estamos en la pantalla de detalle
        onView(withId(R.id.textArtistName)).check(matches(isDisplayed()))
        onView(withId(R.id.textArtistName)).check(matches(withText("Rubén Blades Bellido de Luna")))
        onView(withId(R.id.textArtistDescription)).check(matches(isDisplayed()))
        onView(withId(R.id.textArtistKindBadge)).check(matches(isDisplayed()))
        onView(withId(R.id.textArtistKindBadge)).check(matches(withText("MUSICO")))
        
        // Verificar metadatos (asegurar visibilidad desplazando si es necesario)
        onView(withId(R.id.labelDate)).check(matches(withText("NACIMIENTO")))
        // Scroll to the date view in case it's off-screen on small devices
        onView(withId(R.id.textDate)).perform(scrollTo())
        onView(withId(R.id.textDate)).check(matches(isDisplayed()))
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
