package com.movilesuniandes.vinilos.features.artists

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import android.view.View
import org.hamcrest.Matcher
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed as isDisplayedMatcher
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.movilesuniandes.vinilos.R
import com.movilesuniandes.vinilos.features.artists.view.ArtistListFragment
import org.hamcrest.Matchers.allOf
import androidx.test.espresso.matcher.ViewMatchers.hasSibling
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class ArtistFavoritesE2ETest {

    @Test
    fun toggle_favorite_and_filter_shows_artist() {
        launchFragmentInContainer<ArtistListFragment>(
            themeResId = R.style.Theme_Vinilos,
            factory = TestArtistFragmentFactory(FakeArtistRepository())
        )

        onView(withId(R.id.recyclerViewArtists)).perform(
            RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(
                hasDescendant(withText("Soda Stereo")),
                object : ViewAction {
                    override fun getConstraints(): Matcher<View> = isDisplayed()

                    override fun getDescription(): String = "Click child view with id textFavorite"

                    override fun perform(uiController: UiController, view: View) {
                        val v = view.findViewById<View>(R.id.textFavorite)
                        v.performClick()
                        uiController.loopMainThreadUntilIdle()
                    }
                }
            )
        )

        onView(withId(R.id.btnFilterFavorites)).perform(click())

        onView(withText("Soda Stereo")).check(matches(isDisplayed()))
    }
}
