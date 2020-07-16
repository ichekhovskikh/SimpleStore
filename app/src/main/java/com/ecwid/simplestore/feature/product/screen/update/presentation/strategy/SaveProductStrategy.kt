package com.ecwid.simplestore.feature.product.screen.update.presentation.strategy

import android.graphics.Bitmap

interface SaveProductStrategy {
    fun save(id: Long?, name: String, location: String, price: Long, icon: Bitmap)
}