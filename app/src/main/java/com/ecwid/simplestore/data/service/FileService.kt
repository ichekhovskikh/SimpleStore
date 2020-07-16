package com.ecwid.simplestore.data.service

import android.content.ContentResolver
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ecwid.simplestore.core.provider.DispatcherProvider
import com.ecwid.simplestore.core.tools.Resource
import com.ecwid.simplestore.core.tools.createBitmap
import com.ecwid.simplestore.core.tools.toBase64
import com.ecwid.simplestore.data.dao.ProductDao
import com.ecwid.simplestore.data.model.ProductEntityModel
import com.github.doyaaaaaken.kotlincsv.dsl.csvWriter
import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

interface FileService {

    fun exportProducts(uri: Uri): LiveData<Resource<Unit>>

    fun importProducts(uri: Uri): LiveData<Resource<Unit>>
}

class FileServiceImpl @Inject constructor(
    private val dispatcherProvider: DispatcherProvider,
    private val contentResolver: ContentResolver,
    private val productDao: ProductDao
) : FileService {

    override fun exportProducts(uri: Uri): LiveData<Resource<Unit>> {
        val resource = MutableLiveData<Resource<Unit>>()
        val stream = contentResolver.openOutputStream(uri) ?: return resource.apply {
            postValue(Resource.Error())
        }
        CoroutineScope(dispatcherProvider.io).launch {
            try {
                resource.postValue(Resource.Loading)
                val products = productDao.getAll()
                csvWriter().open(stream) {
                    products.forEach { product ->
                        val id = product.id
                        val name = product.name
                        val price = product.price
                        val address = product.address
                        val latitude = product.latitude
                        val longitude = product.longitude
                        val icon = product.icon?.toBase64()
                        writeRow(listOf(id, name, price, address, latitude, longitude, icon))
                    }
                    flush()
                    close()
                    resource.postValue(Resource.Success(Unit))
                }
            } catch (exception: Exception) {
                resource.postValue(Resource.Error(exception))
            }
        }
        return resource
    }

    override fun importProducts(uri: Uri): LiveData<Resource<Unit>> {
        val resource = MutableLiveData<Resource<Unit>>()
        val stream = contentResolver.openInputStream(uri) ?: return resource.apply {
            postValue(Resource.Error())
        }
        CoroutineScope(dispatcherProvider.io).launch {
            try {
                resource.postValue(Resource.Loading)
                val products = mutableListOf<ProductEntityModel>()
                csvReader().open(stream) {
                    val sequence = readAllAsSequence()
                    sequence.forEach { row ->
                        val id = row[ID_INDEX].toLong()
                        val name = row[NAME_INDEX]
                        val price = row[PRICE_INDEX].toLong()
                        val address = row[ADDRESS_INDEX]
                        val latitude = row[LATITUDE_INDEX].toDoubleOrNull()
                        val longitude = row[LONGITUDE_INDEX].toDoubleOrNull()
                        val icon = createBitmap(row[ICON_INDEX])
                        val product = ProductEntityModel(
                            id,
                            name,
                            price,
                            address,
                            latitude,
                            longitude,
                            icon
                        )
                        products.add(product)
                    }
                    close()
                }
                productDao.updateAll(products)
                resource.postValue(Resource.Success(Unit))
            } catch (exception: Exception) {
                resource.postValue(Resource.Error(exception))
            }
        }
        return resource
    }

    companion object {
        private const val ID_INDEX = 0
        private const val NAME_INDEX = 1
        private const val PRICE_INDEX = 2
        private const val ADDRESS_INDEX = 3
        private const val LATITUDE_INDEX = 4
        private const val LONGITUDE_INDEX = 5
        private const val ICON_INDEX = 6
    }
}