package com.ecwid.simplestore.core.extension

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlin.reflect.KClass

fun <T : ViewModel> ViewModelProvider.Factory.get(
    fragment: Fragment,
    modelClass: KClass<T>
): T = ViewModelProvider(fragment, this).get(modelClass.java)