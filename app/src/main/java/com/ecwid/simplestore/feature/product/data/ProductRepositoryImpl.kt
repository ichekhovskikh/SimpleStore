package com.ecwid.simplestore.feature.product.data

import android.net.Uri
import androidx.lifecycle.LiveData
import com.ecwid.simplestore.core.extension.foreachMap
import com.ecwid.simplestore.core.extension.map
import com.ecwid.simplestore.core.mapper.OneParameterReverseMapper
import com.ecwid.simplestore.core.tools.Resource
import com.ecwid.simplestore.data.model.ProductEntityModel
import com.ecwid.simplestore.data.service.FileService
import com.ecwid.simplestore.data.service.ProductService
import com.ecwid.simplestore.feature.product.domain.ProductRepository
import com.ecwid.simplestore.feature.product.domain.model.ProductModel
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val productService: ProductService,
    private val fileService: FileService,
    private val productMapper: OneParameterReverseMapper<ProductEntityModel, ProductModel>
) : ProductRepository {

    override fun getAll(name: String?) = productService.getAll(name).foreachMap {
        productMapper.map(it)
    }

    override fun getById(id: Long) = productService.getById(id).map { product ->
        product?.let { productMapper.map(it) }
    }

    override fun add(product: ProductModel) {
        val entity = productMapper.reverseMap(product)
        productService.add(entity)
    }

    override fun update(product: ProductModel) {
        val entity = productMapper.reverseMap(product)
        productService.update(entity)
    }

    override fun removeById(id: Long) {
        productService.removeById(id)
    }

    override fun exportProducts(uri: Uri): LiveData<Resource<Unit>> =
        fileService.exportProducts(uri)

    override fun importProducts(uri: Uri): LiveData<Resource<Unit>> =
        fileService.importProducts(uri)
}