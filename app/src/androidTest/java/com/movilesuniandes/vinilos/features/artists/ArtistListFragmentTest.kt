package com.movilesuniandes.vinilos.features.artists

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
import com.movilesuniandes.vinilos.features.artists.view.ArtistListFragment
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ArtistListFragmentTest {

    @Test
    fun encabezado_titulo_es_visible() {
        launchFragmentInContainer<ArtistListFragment>(
            themeResId = R.style.Theme_Vinilos,
            factory = TestArtistFragmentFactory(FakeArtistRepository())
        )
        onView(withId(R.id.textArtistTitle))
            .check(matches(isDisplayed()))
    }

    @Test
    fun encabezado_subtitulo_es_visible() {
        launchFragmentInContainer<ArtistListFragment>(
            themeResId = R.style.Theme_Vinilos,
            factory = TestArtistFragmentFactory(FakeArtistRepository())
        )
        onView(withId(R.id.textArtistSubtitle))
            .check(matches(isDisplayed()))
    }

    @Test
    fun encabezado_titulo_muestra_texto_correcto() {
        launchFragmentInContainer<ArtistListFragment>(
            themeResId = R.style.Theme_Vinilos,
            factory = TestArtistFragmentFactory(FakeArtistRepository())
        )
        onView(withId(R.id.textArtistTitle))
            .check(matches(withText("Catálogo de Artistas")))
    }

    @Test
    fun encabezado_subtitulo_muestra_texto_correcto() {
        launchFragmentInContainer<ArtistListFragment>(
            themeResId = R.style.Theme_Vinilos,
            factory = TestArtistFragmentFactory(FakeArtistRepository())
        )
        onView(withId(R.id.textArtistSubtitle))
            .check(matches(withText("Descubre músicos y bandas")))
    }

    @Test
    fun error_view_inicialmente_oculto() {
        launchFragmentInContainer<ArtistListFragment>(
            themeResId = R.style.Theme_Vinilos,
            factory = TestArtistFragmentFactory(FakeArtistRepository())
        )
        onView(withId(R.id.textErrorArtists))
            .check(matches(withEffectiveVisibility(Visibility.GONE)))
    }
}
