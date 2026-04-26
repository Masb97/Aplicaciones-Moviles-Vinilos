package com.movilesuniandes.vinilos.features.albums

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
import com.movilesuniandes.vinilos.features.albums.view.AlbumListFragment
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AlbumListFragmentTest {

    @Test
    fun encabezado_titulo_es_visible() {
        launchFragmentInContainer<AlbumListFragment>(
            factory = TestAlbumFragmentFactory(FakeAlbumRepository())
        )
        onView(withId(R.id.textTitle))
            .check(matches(isDisplayed()))
    }

    @Test
    fun encabezado_subtitulo_es_visible() {
        launchFragmentInContainer<AlbumListFragment>(
            factory = TestAlbumFragmentFactory(FakeAlbumRepository())
        )
        onView(withId(R.id.textSubtitle))
            .check(matches(isDisplayed()))
    }

    @Test
    fun encabezado_titulo_muestra_texto_correcto() {
        launchFragmentInContainer<AlbumListFragment>(
            factory = TestAlbumFragmentFactory(FakeAlbumRepository())
        )
        onView(withId(R.id.textTitle))
            .check(matches(withText("Catálogo de Álbumes")))
    }

    @Test
    fun encabezado_subtitulo_muestra_texto_correcto() {
        launchFragmentInContainer<AlbumListFragment>(
            factory = TestAlbumFragmentFactory(FakeAlbumRepository())
        )
        onView(withId(R.id.textSubtitle))
            .check(matches(withText("Explora el catálogo")))
    }

    @Test
    fun error_view_inicialmente_oculto() {
        launchFragmentInContainer<AlbumListFragment>(
            factory = TestAlbumFragmentFactory(FakeAlbumRepository())
        )
        onView(withId(R.id.textError))
            .check(matches(withEffectiveVisibility(Visibility.GONE)))
    }

    @Test
    fun catalogo_fake_muestra_albumes() {
        launchFragmentInContainer<AlbumListFragment>(
            factory = TestAlbumFragmentFactory(FakeAlbumRepository())
        )

        onView(withText("Kind of Blue"))
            .check(matches(isDisplayed()))
        onView(withText("The Dark Side of the Moon"))
            .check(matches(isDisplayed()))
    }
}
