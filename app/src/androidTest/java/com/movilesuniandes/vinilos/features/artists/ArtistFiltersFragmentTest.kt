package com.movilesuniandes.vinilos.features.artists

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.google.android.material.button.MaterialButtonToggleGroup
import com.movilesuniandes.vinilos.R
import com.movilesuniandes.vinilos.features.artists.view.ArtistListFragment
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import java.lang.reflect.Field

@RunWith(AndroidJUnit4::class)
@LargeTest
class ArtistFiltersFragmentTest {

    @Test
    fun filtro_bandas_muestra_solo_bandas() {
        launchFragmentInContainer<ArtistListFragment>(
            themeResId = R.style.Theme_Vinilos,
            factory = TestArtistFragmentFactory(FakeArtistRepository())
        )

        onView(withId(R.id.btnFilterBands)).perform(click())

        onView(withText("Soda Stereo")).check(matches(isDisplayed()))
        onView(withText("Rubén Blades")).check(doesNotExist())
    }

    @Test
    fun filtro_musicos_muestra_solo_musicos() {
        launchFragmentInContainer<ArtistListFragment>(
            themeResId = R.style.Theme_Vinilos,
            factory = TestArtistFragmentFactory(FakeArtistRepository())
        )

        onView(withId(R.id.btnFilterMusicians)).perform(click())

        onView(withText("Rubén Blades")).check(matches(isDisplayed()))
        onView(withText("Soda Stereo")).check(doesNotExist())
    }

    @Test
    fun filtro_favoritos_sin_favoritos_muestra_mensaje() {
        val scenario = launchFragmentInContainer<ArtistListFragment>(
            themeResId = R.style.Theme_Vinilos,
            factory = TestArtistFragmentFactory(FakeArtistRepository())
        )

        scenario.onFragment { fragment ->
            fragment.requireView()
                .findViewById<MaterialButtonToggleGroup>(R.id.filterGroup)
                .check(R.id.btnFilterFavorites)
        }

        onView(withText("No tienes artistas favoritos.")).check(matches(isDisplayed()))
    }

    @Test
    fun filtro_favoritos_con_favorito_muestra_artista() {
        val scenario = launchFragmentInContainer<ArtistListFragment>(
            themeResId = R.style.Theme_Vinilos,
            factory = TestArtistFragmentFactory(FakeArtistRepository())
        )

        scenario.onFragment { fragment ->
            val favoriteField = fragment.javaClass.getDeclaredField("favoriteArtistIds")
            favoriteField.isAccessible = true
            @Suppress("UNCHECKED_CAST")
            val favoriteIds = favoriteField.get(fragment) as MutableSet<Int>
            assertTrue(favoriteIds.add(2))

            fragment.requireView()
                .findViewById<MaterialButtonToggleGroup>(R.id.filterGroup)
                .check(R.id.btnFilterFavorites)
        }

        onView(withText("Soda Stereo")).check(matches(isDisplayed()))
    }
}
