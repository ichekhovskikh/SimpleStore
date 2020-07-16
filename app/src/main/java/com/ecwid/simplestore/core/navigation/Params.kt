package com.ecwid.simplestore.core.navigation

import android.os.Parcelable
import com.ecwid.simplestore.core.fragment.BaseFragment

interface Params : Parcelable {
    fun fragment(): BaseFragment
}