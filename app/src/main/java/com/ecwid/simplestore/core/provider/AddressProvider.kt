package com.ecwid.simplestore.core.provider

import android.content.Context
import android.location.Address
import android.location.Geocoder
import java.util.*
import javax.inject.Inject

interface AddressProvider {
    fun getAddress(location: String): Address?
}

class AddressProviderImpl @Inject constructor(
    context: Context
) : AddressProvider {

    private val geoCoder = Geocoder(context, Locale.getDefault())

    override fun getAddress(location: String): Address? =
        geoCoder.getFromLocationName(location, 1).firstOrNull()
}