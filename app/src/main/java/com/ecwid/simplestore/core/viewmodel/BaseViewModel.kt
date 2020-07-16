package com.ecwid.simplestore.core.viewmodel

import androidx.annotation.CallSuper
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel<TriggerType> : ViewModel() {

    protected val trigger = MutableLiveData<TriggerType>()

    @CallSuper
    open fun init(trigger: TriggerType) {
        this.trigger.value = trigger
    }
}