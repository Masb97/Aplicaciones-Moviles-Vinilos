package com.movilesuniandes.vinilos.features.e2e

import android.os.SystemClock
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.PerformException
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.movilesuniandes.vinilos.MainActivity
import com.movilesuniandes.vinilos.R
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.hasEntry
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class ArtistDetailFavoriteE2ETest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun desde_detalle_agregar_a_favoritos_y_verificar_estado() {
        // Ir a Artistas
        onView(allOf(withText("Artistas"), isDisplayed())).perform(click())

        // Esperar listado
        onView(isRoot()).perform(waitForView(withId(R.id.recyclerViewArtists), 12000))

        // Abrir detalle de Rubén Blades
        onView(withText("Rubén Blades Bellido de Luna")).perform(click())

        // Esperar detalle
        onView(isRoot()).perform(waitForView(withId(R.id.textArtistName), 8000))

        // Click en favorito desde detalle
        onView(withId(R.id.textFavorite)).perform(click())

        // Verificar cambio de estado (texto/accion)
        onView(withText("Agregado a favoritos")).check(matches(isDisplayed()))

        // Regresar y filtrar por favoritos para confirmar aparece
        androidx.test.espresso.Espresso.pressBack()
        onView(withId(R.id.btnFilterFavorites)).perform(click())
        onView(withText("Rubén Blades Bellido de Luna")).check(matches(isDisplayed()))
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
