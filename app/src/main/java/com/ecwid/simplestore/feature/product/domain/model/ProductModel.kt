package com.ecwid.simplestore.feature.product.domain.model

import android.graphics.Bitmap

data class ProductModel(
    val id: Long,
    val name: String,
    val price: Long,
    val address: String,
    val latitude: Double?,
    val longitude: Double?,
    val icon: Bitmap?
)