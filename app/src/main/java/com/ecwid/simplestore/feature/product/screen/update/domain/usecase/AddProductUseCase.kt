package com.ecwid.simplestore.feature.product.screen.update.domain.usecase

import com.ecwid.simplestore.feature.product.domain.gateway.ProductGateway
import com.ecwid.simplestore.feature.product.domain.model.ProductModel
import javax.inject.Inject

class AddProductUseCase @Inject constructor(
    private val gateway: ProductGateway
) {
    operator fun invoke(product: ProductModel) = gateway.add(product)
}