package com.ecwid.simplestore.core.mapper

interface OneParameterMapper<X, Y> {
    fun map(source: X): Y
}