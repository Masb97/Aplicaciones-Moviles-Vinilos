package com.movilesuniandes.vinilos.features.collector

import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.Visibility
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.movilesuniandes.vinilos.R
import com.movilesuniandes.vinilos.features.collector.view.CollectorDetailFragment
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CollectorDetailFragmentTest {

    private fun launch() = launchFragmentInContainer<CollectorDetailFragment>(
        fragmentArgs = Bundle().apply {
            putInt(CollectorDetailFragment.ARG_COLLECTOR_ID, 1)
        },
        factory = TestCollectorFragmentFactory(FakeCollectorRepository())
    )

    @Test
    fun nombre_del_coleccionista_es_visible() {
        launch()
        onView(withId(R.id.textCollectorName))
            .check(matches(isDisplayed()))
    }

    @Test
    fun nombre_muestra_texto_correcto() {
        launch()
        onView(withId(R.id.textCollectorName))
            .check(matches(withText("Manolo Bellon")))
    }

    @Test
    fun email_del_coleccionista_es_visible() {
        launch()
        onView(withId(R.id.textCollectorEmail))
            .check(matches(isDisplayed()))
    }

    @Test
    fun telefono_del_coleccionista_es_visible() {
        launch()
        onView(withId(R.id.textCollectorPhone))
            .check(matches(isDisplayed()))
    }

    @Test
    fun stat_albums_muestra_cantidad_correcta() {
        launch()
        onView(withId(R.id.textStatAlbums))
            .check(matches(withText("1")))
    }

    @Test
    fun stat_favoritos_muestra_cantidad_correcta() {
        launch()
        onView(withId(R.id.textStatFavorites))
            .check(matches(withText("1")))
    }

    @Test
    fun artista_favorito_es_visible_en_lista() {
        launch()
        onView(withText("The Beatles"))
            .check(matches(isDisplayed()))
    }

    @Test
    fun error_view_inicialmente_oculto() {
        launch()
        onView(withId(R.id.textError))
            .check(matches(withEffectiveVisibility(Visibility.GONE)))
    }
}
