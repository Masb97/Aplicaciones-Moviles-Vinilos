package com.movilesuniandes.vinilos.features.albums

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
import com.movilesuniandes.vinilos.features.albums.view.AlbumDetailFragment
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AlbumDetailFragmentTest {

    private fun argsForAlbum(id: Int) = Bundle().apply { putInt("albumId", id) }

    @Test
    fun muestra_estado_exitoso_con_datos_del_album() {
        launchFragmentInContainer(
            fragmentArgs = argsForAlbum(1),
            themeResId = R.style.Theme_Vinilos
        ) {
            AlbumDetailFragment(FakeAlbumRepository())
        }

        onView(withId(R.id.textAlbumName)).check(matches(withText("Kind of Blue")))
        onView(withId(R.id.textAlbumDescription)).check(matches(withText("Jazz modal")))
        onView(withId(R.id.textAlbumGenre)).check(matches(withText("Jazz")))
        onView(withId(R.id.textRecordLabel)).check(matches(withText("Columbia")))
        onView(withId(R.id.textAlbumReleaseYear)).check(matches(withText("1959")))
    }

    @Test
    fun caratula_del_album_es_visible_en_estado_exitoso() {
        launchFragmentInContainer(
            fragmentArgs = argsForAlbum(1),
            themeResId = R.style.Theme_Vinilos
        ) {
            AlbumDetailFragment(FakeAlbumRepository())
        }

        onView(withId(R.id.imageAlbumCover)).check(matches(isDisplayed()))
    }

    @Test
    fun progress_y_error_estan_ocultos_en_estado_exitoso() {
        launchFragmentInContainer(
            fragmentArgs = argsForAlbum(1),
            themeResId = R.style.Theme_Vinilos
        ) {
            AlbumDetailFragment(FakeAlbumRepository())
        }

        onView(withId(R.id.progressBar)).check(matches(withEffectiveVisibility(Visibility.GONE)))
        onView(withId(R.id.textError)).check(matches(withEffectiveVisibility(Visibility.GONE)))
    }

    @Test
    fun muestra_estado_error_cuando_falla_el_repositorio() {
        launchFragmentInContainer(
            fragmentArgs = argsForAlbum(99),
            themeResId = R.style.Theme_Vinilos
        ) {
            AlbumDetailFragment(FakeAlbumRepositoryWithError())
        }

        onView(withId(R.id.textError)).check(matches(isDisplayed()))
        onView(withId(R.id.textAlbumName)).check(matches(withEffectiveVisibility(Visibility.GONE)))
    }
}
