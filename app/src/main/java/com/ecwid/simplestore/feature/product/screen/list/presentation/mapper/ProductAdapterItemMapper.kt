package com.ecwid.simplestore.feature.product.screen.list.presentation.mapper

import com.ecwid.simplestore.core.mapper.OneParameterMapper
import com.ecwid.simplestore.feature.product.domain.model.ProductModel
import com.ecwid.simplestore.feature.product.screen.list.presentation.model.ProductAdapterItemModel

class ProductAdapterItemMapper : OneParameterMapper<ProductModel, ProductAdapterItemModel> {

    override fun map(source: ProductModel) = ProductAdapterItemModel(
        id = source.id,
        name = source.name,
        price = source.price,
        address = source.address,
        latitude = source.latitude,
        longitude = source.longitude,
        icon = source.icon
    )
}