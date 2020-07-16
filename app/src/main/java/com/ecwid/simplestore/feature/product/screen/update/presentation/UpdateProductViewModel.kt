package com.ecwid.simplestore.feature.product.screen.update.presentation

import android.graphics.Bitmap
import androidx.lifecycle.MutableLiveData
import com.ecwid.simplestore.core.extension.doNext
import com.ecwid.simplestore.core.extension.filter
import com.ecwid.simplestore.core.extension.map
import com.ecwid.simplestore.core.extension.switchMap
import com.ecwid.simplestore.core.mapper.OneParameterMapper
import com.ecwid.simplestore.core.tools.isSame
import com.ecwid.simplestore.core.viewmodel.BaseViewModel
import com.ecwid.simplestore.feature.product.domain.model.ProductModel
import com.ecwid.simplestore.feature.product.screen.update.domain.usecase.GetProductByIdUseCase
import com.ecwid.simplestore.feature.product.screen.update.domain.usecase.RemoveProductByIdUseCase
import com.ecwid.simplestore.feature.product.screen.update.presentation.model.ProductViewItemModel
import com.ecwid.simplestore.feature.product.screen.update.presentation.strategy.SaveProductStrategy
import javax.inject.Inject

class UpdateProductViewModel @Inject constructor(
    private val productId: Long?,
    private val saveProductStrategy: SaveProductStrategy,
    private val getProductByIdUseCase: GetProductByIdUseCase,
    private val removeProductByIdUseCase: RemoveProductByIdUseCase,
    private val productViewItemMapper: OneParameterMapper<ProductModel, ProductViewItemModel>
) : BaseViewModel<Unit>() {

    private var recentProduct: ProductModel? = null

    private var name: String? = null
    private var address: String? = null
    private var price: Long? = null
    private var icon: Bitmap? = null

    val productLiveData = trigger
        .map { productId }
        .filter { it != null }
        .switchMap { getProductByIdUseCase(it ?: 0) }
        .doNext { recentProduct = it }
        .map { product -> product?.let { productViewItemMapper.map(it) } }

    val isDeleteAvailableLiveData = trigger.map { productId != null }

    private val saveEnabledTrigger = MutableLiveData<Unit>()
    val isSaveEnabledLiveData = saveEnabledTrigger.map { isSaveEnabled }

    fun updateName(name: String?) {
        this.name = name
        saveEnabledTrigger.postValue(Unit)
    }

    fun updateAddress(address: String?) {
        this.address = address
        saveEnabledTrigger.postValue(Unit)
    }

    fun updatePrice(price: Long?) {
        this.price = price
        saveEnabledTrigger.postValue(Unit)
    }

    fun updateIcon(icon: Bitmap?) {
        this.icon = icon
        saveEnabledTrigger.postValue(Unit)
    }

    fun saveProduct() {
        val name = name ?: return
        val address = address ?: return
        val price = price ?: return
        val icon = icon ?: return
        saveProductStrategy.save(productId, name, address, price, icon)
    }

    fun deleteProduct() {
        val productId = productId ?: return
        removeProductByIdUseCase(productId)
    }

    private val isSaveEnabled: Boolean
        get() = isProductChanged && !name.isNullOrBlank() &&
                !address.isNullOrBlank() && price != null && icon != null

    private val isProductChanged: Boolean
        get() = name != recentProduct?.name || address != recentProduct?.address ||
                price != recentProduct?.price || !icon.isSame(recentProduct?.icon)
}