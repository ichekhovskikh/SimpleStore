package com.ecwid.simplestore.feature.product.domain.gateway

import android.net.Uri
import androidx.lifecycle.LiveData
import com.ecwid.simplestore.core.tools.Resource
import com.ecwid.simplestore.feature.product.domain.model.ProductModel

interface ProductGateway {

    fun getAll(name: String?): LiveData<List<ProductModel>>

    fun getById(id: Long): LiveData<ProductModel>

    fun add(product: ProductModel)

    fun update(product: ProductModel)

    fun removeById(id: Long)

    fun exportProducts(uri: Uri): LiveData<Resource<Unit>>

    fun importProducts(uri: Uri): LiveData<Resource<Unit>>
}