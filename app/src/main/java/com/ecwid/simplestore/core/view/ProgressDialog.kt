package com.ecwid.simplestore.core.view

import android.app.Dialog
import android.content.Context
import com.ecwid.simplestore.R

class ProgressDialog(context: Context) : Dialog(context) {

    init {
        window?.setBackgroundDrawableResource(android.R.color.transparent)
        setCancelable(false)
        setCanceledOnTouchOutside(false)
        setContentView(R.layout.view_progress_dialog)
    }
}
