package com.ecwid.simplestore.feature.product.screen.update.presentation.mapper

import com.ecwid.simplestore.core.mapper.OneParameterMapper
import com.ecwid.simplestore.feature.product.domain.model.ProductModel
import com.ecwid.simplestore.feature.product.screen.update.presentation.model.ProductViewItemModel

class ProductViewItemMapper : OneParameterMapper<ProductModel, ProductViewItemModel> {

    override fun map(source: ProductModel) = ProductViewItemModel(
        name = source.name,
        price = source.price,
        address = source.address,
        icon = source.icon
    )
}