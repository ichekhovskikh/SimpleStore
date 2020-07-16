package com.ecwid.simplestore.core.mapper

interface OneParameterReverseMapper<X, Y> : OneParameterMapper<X, Y> {
    fun reverseMap(source: Y): X
}