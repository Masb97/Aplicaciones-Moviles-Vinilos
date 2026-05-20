package com.movilesuniandes.vinilos.core.testing

import android.view.View
import com.google.android.material.textfield.TextInputLayout
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher

fun hasTextInputLayoutErrorText(expectedSubstring: String): Matcher<View> {
    return object : TypeSafeMatcher<View>(View::class.java) {
        override fun describeTo(description: Description) {
            description.appendText("TextInputLayout with error text containing: $expectedSubstring")
        }

        override fun matchesSafely(view: View): Boolean {
            if (view !is TextInputLayout) return false
            val err = view.error ?: return false
            return err.toString().contains(expectedSubstring)
        }
    }
}
