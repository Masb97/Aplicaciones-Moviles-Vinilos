package com.movilesuniandes.vinilos.features.albums

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.movilesuniandes.vinilos.R
import com.movilesuniandes.vinilos.features.albums.view.CreateAlbumFragment
import org.junit.Test
import org.junit.runner.RunWith
import org.hamcrest.Matchers

@RunWith(AndroidJUnit4::class)
@LargeTest
class CreateAlbumFragmentTest {

    @Test
    fun create_album_happy_path_shows_success() {
        launchFragmentInContainer<CreateAlbumFragment>(
            themeResId = R.style.Theme_Vinilos,
            factory = TestAlbumFragmentFactory(FakeAlbumRepository())
        )

        onView(withId(R.id.inputName)).perform(replaceText("A Night at the Opera"))
        onView(withId(R.id.inputReleaseDate)).perform(replaceText("1975-11-21"))
        onView(withId(R.id.inputGenre)).perform(replaceText("Rock"))

        onView(withId(R.id.btnCreateAlbum)).perform(click())

        // Expect no visible error text
        onView(withId(R.id.textErrorCreate)).check(matches(org.hamcrest.Matchers.not(isDisplayed())))
    }
}
