package com.movilesuniandes.vinilos.features.artists

import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.movilesuniandes.vinilos.R
import com.movilesuniandes.vinilos.features.artists.model.ArtistKind
import com.movilesuniandes.vinilos.features.artists.view.ArtistDetailFragment
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ArtistDetailFragmentTest {

    @Test
    fun displaySuccessState() {
        val fragmentArgs = Bundle().apply {
            putInt("artistId", 1)
            putString("artistKind", ArtistKind.MUSICO.name)
        }
        
        // Usamos el repositorio fake para la prueba
        val repository = FakeArtistRepository()
        
        launchFragmentInContainer(fragmentArgs, themeResId = R.style.Theme_Vinilos) {
            ArtistDetailFragment(repository)
        }

        onView(withId(R.id.textArtistName)).check(matches(withText("Rubén Blades")))
        onView(withId(R.id.textArtistDescription)).check(matches(withText("Cantautor")))
        onView(withId(R.id.textArtistKindBadge)).check(matches(withText("MUSICO")))
        onView(withId(R.id.textDate)).check(matches(withText("1948-07-16")))
    }

    @Test
    fun displayErrorStateAndRetry() {
        val fragmentArgs = Bundle().apply {
            putInt("artistId", 1)
            putString("artistKind", ArtistKind.MUSICO.name)
        }
        
        val repository = FakeArtistRepositoryWithError()
        
        launchFragmentInContainer(fragmentArgs, themeResId = R.style.Theme_Vinilos) {
            ArtistDetailFragment(repository)
        }

        // Verificar que el error es visible
        onView(withId(R.id.errorContainer)).check(matches(isDisplayed()))
        onView(withId(R.id.btnRetry)).check(matches(isDisplayed()))
        
        // Simular clic en reintentar
        onView(withId(R.id.btnRetry)).perform(click())
        
        // Sigue en error porque el repositorio fake siempre falla
        onView(withId(R.id.errorContainer)).check(matches(isDisplayed()))
    }
    
    @Test
    fun displayBandDetail() {
        val fragmentArgs = Bundle().apply {
            putInt("artistId", 2)
            putString("artistKind", ArtistKind.BANDA.name)
        }
        
        val repository = FakeArtistRepository()
        
        launchFragmentInContainer(fragmentArgs, themeResId = R.style.Theme_Vinilos) {
            ArtistDetailFragment(repository)
        }

        onView(withId(R.id.textArtistName)).check(matches(withText("Soda Stereo")))
        onView(withId(R.id.textArtistKindBadge)).check(matches(withText("BANDA")))
        onView(withId(R.id.labelDate)).check(matches(withText("FORMACIÓN")))
        onView(withId(R.id.textDate)).check(matches(withText("1982-01-01")))
    }
}
