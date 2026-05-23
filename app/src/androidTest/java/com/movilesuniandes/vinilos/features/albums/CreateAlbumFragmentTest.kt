package com.movilesuniandes.vinilos.features.albums

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.scrollTo
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.movilesuniandes.vinilos.R
import com.movilesuniandes.vinilos.features.albums.view.CreateAlbumFragment
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.junit.Test
import org.junit.runner.RunWith
import android.view.View
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import org.hamcrest.Matchers.anyOf

@RunWith(AndroidJUnit4::class)
@LargeTest
class CreateAlbumFragmentTest {

    @Test
    fun create_album_happy_path_shows_success() {
        launchFragmentInContainer<CreateAlbumFragment>(
            themeResId = R.style.Theme_Vinilos,
            factory = TestAlbumFragmentFactory(FakeAlbumRepository())
        )

        onView(withId(R.id.inputName)).perform(setTextDirect("A Night at the Opera"))
        onView(withId(R.id.inputReleaseDate)).perform(setTextDirect("1975-11-21T00:00:00Z"))
        onView(withId(R.id.inputGenre)).perform(setTextDirect("Rock"))
        onView(withId(R.id.inputRecordLabel)).perform(setTextDirect("EMI"))
        onView(withId(R.id.inputDescription)).perform(setTextDirect("Descripción breve"))
        onView(withId(R.id.inputCover)).perform(setTextDirect("https://example.com/queen.png"))

        // Ensure button is visible before clicking (scroll if needed)
        onView(withId(R.id.btnCreateAlbum)).perform(scrollTo(), click())

        // Wait briefly for success container to appear
        Thread.sleep(300)
        onView(withId(R.id.cardSuccessContainer)).check(matches(isDisplayed()))
        onView(withId(R.id.textSuccessTitle)).check(matches(withText("Álbum creado correctamente")))
        onView(withId(R.id.textSuccessAlbumName)).check(matches(withText("A Night at the Opera")))
        onView(withId(R.id.textSuccessGenreValue)).check(matches(withText("Rock")))
        onView(withId(R.id.textSuccessYearValue)).check(matches(withText("1975")))
    }

    private fun setTextDirect(text: String): ViewAction {
        return object : ViewAction {
            override fun getDescription(): String = "Set text directly"

            override fun getConstraints(): Matcher<View> = anyOf(
                isAssignableFrom(TextInputEditText::class.java),
                isAssignableFrom(MaterialAutoCompleteTextView::class.java)
            )

            override fun perform(uiController: UiController, view: View) {
                if (view is TextInputEditText) {
                    view.setText(text)
                } else if (view is MaterialAutoCompleteTextView) {
                    view.setText(text, false)
                }
                uiController.loopMainThreadUntilIdle()
            }
        }
    }
}
