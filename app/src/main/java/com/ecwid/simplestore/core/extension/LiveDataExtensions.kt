package com.ecwid.simplestore.core.extension

import androidx.lifecycle.*
import com.ecwid.simplestore.core.tools.Resource

fun <T> LiveData<T>.observe(owner: LifecycleOwner, callback: (data: T) -> Unit) {
    observe(owner, Observer { data ->
        data?.let { callback.invoke(it) }
    })
}

fun <X> LiveData<X>.doNext(body: (X?) -> Unit): LiveData<X> {
    val result = MediatorLiveData<X>()
    result.addSource(this) { x ->
        body(x)
        result.value = x
    }
    return result
}

fun <X> LiveData<X>.filter(condition: (X?) -> Boolean): LiveData<X> {
    val result = MediatorLiveData<X>()
    result.addSource(this) { x -> if (condition(x)) result.value = x }
    return result
}

fun <X, Y> LiveData<X>.map(body: (X?) -> Y?): LiveData<Y> {
    return Transformations.map(this, body)
}

fun <X, Y> LiveData<X>.switchMap(body: (X?) -> LiveData<Y>): LiveData<Y> {
    return Transformations.switchMap(this, body)
}

fun <X, Y> LiveData<List<X>>.foreachMap(body: (X) -> Y): LiveData<List<Y>> {
    return map { x -> x?.map { body(it) } }
}

fun <X> MediatorLiveData<Resource<X>>.addResource(source: LiveData<Resource<X>>) {
    addSource(source) {
        postValue(it)
        if (it is Resource.Success || it is Resource.Error) {
            removeSource(source)
        }
    }
}