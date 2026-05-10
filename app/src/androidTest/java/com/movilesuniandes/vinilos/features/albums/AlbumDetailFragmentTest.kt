package com.movilesuniandes.vinilos.features.albums

import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.movilesuniandes.vinilos.features.albums.view.AlbumDetailFragment
import com.movilesuniandes.vinilos.R
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AlbumDetailFragmentTest {
    @Test
    fun displaySuccessState(){
        val fragmentArgs= Bundle().apply{
            putInt("albumId",1)
        }

        val repository= FakeAlbumRepository()

        launchFragmentInContainer<AlbumDetailFragment>(
            fragmentArgs,
            themeResId= R.style.Theme_Vinilos) {
            AlbumDetailFragment(repository)
        }

        onView(withId(R.id.textAlbumName)).check(matches(withText("Kind of Blue")))
        onView(withId(R.id.textAlbumReleaseYear)).check(matches(withText("1959")))
        onView(withId(R.id.textAlbumDescription)).check(matches(withText("Jazz modal")))
        onView(withId(R.id.textAlbumGenre)).check(matches(withText("Jazz")))
        onView(withId(R.id.textRecordLabel)).check(matches(withText("Columbia")))

    }

    @Test
    fun displayErrorState() {
        val fragmentArgs = Bundle().apply {
            putInt("albumId", 1)
        }
        val repository = FakeAlbumRepositoryWithError()

        launchFragmentInContainer<AlbumDetailFragment>(
            fragmentArgs,
            themeResId = R.style.Theme_Vinilos
        ) {
            AlbumDetailFragment(repository)
        }

        onView(withId(R.id.textError)).check(matches(isDisplayed()))
        onView(withId(R.id.textError)).check(matches(withText("Error de conexión")))
    }

}