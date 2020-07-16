package com.ecwid.simplestore.feature.product.screen.list.presentation.model

import android.graphics.Bitmap
import com.ecwid.simplestore.core.adapter.item.AdapterItemModel

class ProductAdapterItemModel(
    val id: Long,
    val name: String,
    val price: Long,
    val address: String,
    val latitude: Double?,
    val longitude: Double?,
    val icon: Bitmap?
) : AdapterItemModel