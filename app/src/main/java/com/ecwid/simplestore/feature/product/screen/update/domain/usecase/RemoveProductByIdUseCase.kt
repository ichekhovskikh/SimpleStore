package com.ecwid.simplestore.feature.product.screen.update.domain.usecase

import com.ecwid.simplestore.feature.product.domain.gateway.ProductGateway
import javax.inject.Inject

class RemoveProductByIdUseCase @Inject constructor(
    private val gateway: ProductGateway
) {
    operator fun invoke(id: Long) = gateway.removeById(id)
}