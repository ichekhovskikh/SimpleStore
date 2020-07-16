package com.ecwid.simplestore.feature.product.screen.list.presentation

import android.content.res.Resources
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.ecwid.simplestore.R
import com.ecwid.simplestore.core.adapter.item.EmptyAdapterItemModel
import com.ecwid.simplestore.core.extension.*
import com.ecwid.simplestore.core.mapper.OneParameterMapper
import com.ecwid.simplestore.core.tools.Resource
import com.ecwid.simplestore.core.viewmodel.BaseViewModel
import com.ecwid.simplestore.feature.product.domain.model.ProductModel
import com.ecwid.simplestore.feature.product.screen.list.domain.usecase.ExportProductsUseCase
import com.ecwid.simplestore.feature.product.screen.list.domain.usecase.GetProductAllUseCase
import com.ecwid.simplestore.feature.product.screen.list.domain.usecase.ImportProductsUseCase
import com.ecwid.simplestore.feature.product.screen.list.presentation.model.ProductAdapterItemModel
import javax.inject.Inject

class ListProductViewModel @Inject constructor(
    private val resources: Resources,
    private val getProductAllUseCase: GetProductAllUseCase,
    private val importProductsUseCase: ImportProductsUseCase,
    private val exportProductsUseCase: ExportProductsUseCase,
    private val productAdapterItemMapper: OneParameterMapper<ProductModel, ProductAdapterItemModel>
) : BaseViewModel<String?>() {

    private val resourceMediatorLiveData = MediatorLiveData<Resource<Unit>>()

    val loaderLiveData: LiveData<Boolean> = resourceMediatorLiveData
        .map { it is Resource.Loading }

    val errorLiveData: LiveData<String> = resourceMediatorLiveData
        .filter { it is Resource.Error }
        .map { resources.getString(R.string.product_file_error) }

    val itemsLiveData = trigger
        .switchMap { search -> getProductAllUseCase(search) }
        .foreachMap { productAdapterItemMapper.map(it) }
        .map {
            when {
                it.isNullOrEmpty() -> listOf(
                    EmptyAdapterItemModel(resources.getString(R.string.product_empty))
                )
                else -> it
            }
        }

    fun onSearchTextChanged(text: String?) {
        trigger.postValue(text)
    }

    fun importProducts(uri: Uri) {
        val source = importProductsUseCase(uri)
        resourceMediatorLiveData.addResource(source)
    }

    fun exportProducts(uri: Uri) {
        val source = exportProductsUseCase(uri)
        resourceMediatorLiveData.addResource(source)
    }
}