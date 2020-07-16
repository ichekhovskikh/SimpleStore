package com.ecwid.simplestore.data.service

import androidx.lifecycle.LiveData
import com.ecwid.simplestore.core.provider.DispatcherProvider
import com.ecwid.simplestore.data.dao.ProductDao
import com.ecwid.simplestore.data.model.ProductEntityModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

interface ProductService {

    fun getAll(name: String?): LiveData<List<ProductEntityModel>>

    fun getById(id: Long): LiveData<ProductEntityModel>

    fun add(product: ProductEntityModel)

    fun update(product: ProductEntityModel)

    fun removeById(id: Long)

    fun updateAll(products: List<ProductEntityModel>)
}

class ProductServiceImpl @Inject constructor(
    private val dispatcherProvider: DispatcherProvider,
    private val productDao: ProductDao
) : ProductService {

    override fun getAll(name: String?) = productDao.getAllLive(name)

    override fun getById(id: Long) = productDao.getByIdLive(id)

    override fun add(product: ProductEntityModel) {
        CoroutineScope(dispatcherProvider.io).launch {
            productDao.add(product)
        }
    }

    override fun update(product: ProductEntityModel) {
        CoroutineScope(dispatcherProvider.io).launch {
            productDao.update(product)
        }
    }

    override fun removeById(id: Long) {
        CoroutineScope(dispatcherProvider.io).launch {
            productDao.removeById(id)
        }
    }

    override fun updateAll(products: List<ProductEntityModel>) {
        CoroutineScope(dispatcherProvider.io).launch {
            productDao.updateAll(products)
        }
    }
}