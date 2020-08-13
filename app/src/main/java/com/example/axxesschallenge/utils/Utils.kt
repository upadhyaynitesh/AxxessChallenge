package com.example.axxesschallenge.utils

import android.view.View
import android.view.inputmethod.InputMethodManager

object Utils {
    /*Hide the keyboard*/
    fun View.hideKeyboard() {
        val inputMethodManager =
            context!!.getSystemService(android.content.Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        inputMethodManager?.hideSoftInputFromWindow(windowToken, 0)
    }
}