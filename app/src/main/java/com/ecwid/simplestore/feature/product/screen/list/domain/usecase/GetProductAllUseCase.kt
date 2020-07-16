package com.ecwid.simplestore.feature.product.screen.list.domain.usecase

import com.ecwid.simplestore.feature.product.domain.gateway.ProductGateway
import javax.inject.Inject

class GetProductAllUseCase @Inject constructor(
    private val gateway: ProductGateway
) {
    operator fun invoke(name: String?) = gateway.getAll(name)
}