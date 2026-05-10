package com.movilesuniandes.vinilos.features.collector

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
import com.movilesuniandes.vinilos.features.collector.view.CollectorListFragment
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CollectorListFragmentTest {

    @Test
    fun encabezado_titulo_es_visible() {
        launchFragmentInContainer<CollectorListFragment>(
            themeResId = R.style.Theme_Vinilos,
            factory = TestCollectorFragmentFactory(FakeCollectorRepository())
        )
        onView(withId(R.id.textTitle))
            .check(matches(isDisplayed()))
    }

    @Test
    fun encabezado_subtitulo_es_visible() {
        launchFragmentInContainer<CollectorListFragment>(
            themeResId = R.style.Theme_Vinilos,
            factory = TestCollectorFragmentFactory(FakeCollectorRepository())
        )
        onView(withId(R.id.textSubtitle))
            .check(matches(isDisplayed()))
    }

    @Test
    fun encabezado_titulo_muestra_texto_correcto() {
        launchFragmentInContainer<CollectorListFragment>(
            themeResId = R.style.Theme_Vinilos,
            factory = TestCollectorFragmentFactory(FakeCollectorRepository())
        )
        onView(withId(R.id.textTitle))
            .check(matches(withText("Catálogo Coleccionistas")))
    }

    @Test
    fun encabezado_subtitulo_muestra_texto_correcto() {
        launchFragmentInContainer<CollectorListFragment>(
            themeResId = R.style.Theme_Vinilos,
            factory = TestCollectorFragmentFactory(FakeCollectorRepository())
        )
        onView(withId(R.id.textSubtitle))
            .check(matches(withText("Explora la red de coleccionistas que mantienen viva la cultura del sonido")))
    }

    @Test
    fun error_view_inicialmente_oculto_en_caso_exitoso() {
        launchFragmentInContainer<CollectorListFragment>(
            themeResId = R.style.Theme_Vinilos,
            factory = TestCollectorFragmentFactory(FakeCollectorRepository())
        )
        onView(withId(R.id.textError))
            .check(matches(withEffectiveVisibility(Visibility.GONE)))
    }

    @Test
    fun recyclerview_es_visible_cuando_carga_datos() {
        launchFragmentInContainer<CollectorListFragment>(
            themeResId = R.style.Theme_Vinilos,
            factory = TestCollectorFragmentFactory(FakeCollectorRepository())
        )
        onView(withId(R.id.recyclerView))
            .check(matches(isDisplayed()))
    }

    @Test
    fun catalogo_fake_muestra_coleccionistas() {
        launchFragmentInContainer<CollectorListFragment>(
            themeResId = R.style.Theme_Vinilos,
            factory = TestCollectorFragmentFactory(FakeCollectorRepository())
        )

        onView(withText("Manolo Bellon"))
            .check(matches(isDisplayed()))
        onView(withText("Jaime Monsalve"))
            .check(matches(isDisplayed()))
    }

    @Test
    fun muestra_email_de_coleccionistas() {
        launchFragmentInContainer<CollectorListFragment>(
            themeResId = R.style.Theme_Vinilos,
            factory = TestCollectorFragmentFactory(FakeCollectorRepository())
        )

        onView(withText("manollo@carcol.com.co"))
            .check(matches(isDisplayed()))
    }

    @Test
    fun estado_error_muestra_mensaje_y_oculta_lista() {
        launchFragmentInContainer<CollectorListFragment>(
            themeResId = R.style.Theme_Vinilos,
            factory = TestCollectorFragmentFactory(FakeCollectorRepositoryWithError())
        )

        onView(withId(R.id.textError))
            .check(matches(isDisplayed()))
        onView(withId(R.id.recyclerView))
            .check(matches(withEffectiveVisibility(Visibility.GONE)))
    }
}
