package com.ecwid.simplestore.core.extension

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.ecwid.simplestore.core.tools.TextWatcherWrapper

fun EditText.onTextChanged(listener: (s: CharSequence?, start: Int, before: Int, count: Int) -> Unit) {
    addTextChangedListener(object : TextWatcherWrapper() {
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            listener(s, start, before, count)
        }
    })
}

fun View.hideKeyboard() {
    val context = context ?: return
    val windowToken = windowToken ?: return
    val inputMethodManager = context.getSystemService(
        Context.INPUT_METHOD_SERVICE
    ) as? InputMethodManager
    inputMethodManager?.hideSoftInputFromWindow(windowToken, 0)
}