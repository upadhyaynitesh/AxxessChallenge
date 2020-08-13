package com.example.axxesschallenge.utils

import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import androidx.databinding.BindingAdapter

@BindingAdapter("goneUnless")
fun goneUnless(view: View, visible: Boolean) {
    view.visibility = if (visible) View.VISIBLE else View.GONE
}

@BindingAdapter("textChangedListener")
fun onTextChanged(view: EditText, textWatcher: TextWatcher) {
    view.addTextChangedListener(textWatcher)
}