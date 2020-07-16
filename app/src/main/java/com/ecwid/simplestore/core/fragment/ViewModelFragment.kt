package com.ecwid.simplestore.core.fragment

import android.os.Bundle
import androidx.lifecycle.ViewModel
import com.ecwid.simplestore.core.extension.get
import com.ecwid.simplestore.di.tools.ViewModelFactory
import javax.inject.Inject
import kotlin.reflect.KClass

abstract class ViewModelFragment<VM : ViewModel> : BaseFragment() {

    protected lateinit var viewModel: VM
    protected abstract val viewModelClass: KClass<VM>

    @Inject
    protected lateinit var viewModelFactory: ViewModelFactory

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        createViewModel()
    }

    private fun createViewModel() {
        viewModel = viewModelFactory.get(this, viewModelClass)
    }
}