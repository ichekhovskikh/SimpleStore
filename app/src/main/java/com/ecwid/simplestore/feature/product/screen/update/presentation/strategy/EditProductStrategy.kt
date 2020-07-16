package com.ecwid.simplestore.feature.product.screen.update.presentation.strategy

import android.graphics.Bitmap
import com.ecwid.simplestore.core.provider.AddressProvider
import com.ecwid.simplestore.feature.product.domain.model.ProductModel
import com.ecwid.simplestore.feature.product.screen.update.domain.usecase.UpdateProductUseCase

class EditProductStrategy(
    private val addressProvider: AddressProvider,
    private val updateProductUseCase: UpdateProductUseCase
) : SaveProductStrategy {

    override fun save(id: Long?, name: String, location: String, price: Long, icon: Bitmap) {
        val address = addressProvider.getAddress(location)
        val model = ProductModel(
            id = id ?: 0,
            name = name,
            price = price,
            address = location,
            latitude = address?.latitude,
            longitude = address?.longitude,
            icon = icon
        )
        updateProductUseCase(model)
    }
}