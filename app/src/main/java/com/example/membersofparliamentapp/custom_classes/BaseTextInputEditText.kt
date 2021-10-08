package com.example.membersofparliamentapp.custom_classes

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import com.example.membersofparliamentapp.functions.hideKeyboard
import com.google.android.material.textfield.TextInputEditText

/**     Markus Nivasalo, 23.9.2021
 *
 *      Class for custom EditText that hides the keyboard if the text field isn't active. Couldn't
 *      figure out a solution for this myself so this is straight from StackOverflow. Link checked
 *      8.10.2021:
 *
 *      https://stackoverflow.com/questions/4165414/how-to-hide-soft-keyboard-on-android-after-clicking-outside-edittext
 *
 */


class BaseTextInputEditText(context: Context, attrs: AttributeSet?) : TextInputEditText(context, attrs){
    override fun onFocusChanged(focused: Boolean,
                                direction: Int,
                                previouslyFocusedRect: Rect?) {
        super.onFocusChanged(focused,
            direction,
            previouslyFocusedRect)
        if (!focused) this.hideKeyboard()
    }
}