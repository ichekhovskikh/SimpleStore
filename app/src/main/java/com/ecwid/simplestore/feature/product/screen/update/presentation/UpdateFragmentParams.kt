package com.ecwid.simplestore.feature.product.screen.update.presentation

import com.ecwid.simplestore.core.navigation.Params
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UpdateFragmentParams(
    val id: Long? = null,
    val transitionName: String? = null
) : Params {

    override fun fragment() = UpdateProductFragment()
}