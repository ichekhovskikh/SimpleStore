package com.ecwid.simplestore.feature.product.screen.list.presentation.adapter.differ

class ProductChangePayload(
    val isChangedName: Boolean = false,
    val isChangedPrice: Boolean = false,
    val isChangedAddress: Boolean = false,
    val isChangedIcon: Boolean = false
)