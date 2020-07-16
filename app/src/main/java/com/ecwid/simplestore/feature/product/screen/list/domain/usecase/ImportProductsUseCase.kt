package com.ecwid.simplestore.feature.product.screen.list.domain.usecase

import android.net.Uri
import androidx.lifecycle.LiveData
import com.ecwid.simplestore.core.tools.Resource
import com.ecwid.simplestore.feature.product.domain.gateway.ProductGateway
import javax.inject.Inject

class ImportProductsUseCase @Inject constructor(
    private val gateway: ProductGateway
) {
    operator fun invoke(uri: Uri): LiveData<Resource<Unit>> =
        gateway.importProducts(uri)
}