package com.example.axxesschallenge.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AlertDialog

object Utils {
    /*Hide the keyboard*/
    fun View.hideKeyboard() {
        val inputMethodManager =
            context!!.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        inputMethodManager?.hideSoftInputFromWindow(windowToken, 0)
    }

    /*Adding @JvmStatic annotation helps in calling Kotlin functions defined in Object to be called as
    * static functions in Java class. As we are using showAlertDialog from both MainFragment(Kotlin) and
    * DetailsFragment(Java) */
    @JvmStatic
    fun showAlertDialog(
        context: Context,
        title: String,
        message: String,
        positiveButton: String
    ) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setPositiveButton(
            positiveButton
        ) { dialog, id ->
            dialog.dismiss()
        }
        builder.setCancelable(false)
        builder.show()
    }
}