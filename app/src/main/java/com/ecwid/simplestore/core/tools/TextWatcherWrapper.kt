package com.ecwid.simplestore.core.tools

import android.text.Editable
import android.text.TextWatcher

open class TextWatcherWrapper : TextWatcher {

    override fun afterTextChanged(s: Editable?) {
        // Nothing
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        // Nothing
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        // Nothing
    }

}
