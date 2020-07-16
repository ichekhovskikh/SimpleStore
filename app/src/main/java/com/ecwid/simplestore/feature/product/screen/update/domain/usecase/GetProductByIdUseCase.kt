package com.ecwid.simplestore.feature.product.screen.update.domain.usecase

import androidx.lifecycle.LiveData
import com.ecwid.simplestore.feature.product.domain.gateway.ProductGateway
import com.ecwid.simplestore.feature.product.domain.model.ProductModel
import javax.inject.Inject

class GetProductByIdUseCase @Inject constructor(
    private val gateway: ProductGateway
) {
    operator fun invoke(id: Long): LiveData<ProductModel> = gateway.getById(id)
}