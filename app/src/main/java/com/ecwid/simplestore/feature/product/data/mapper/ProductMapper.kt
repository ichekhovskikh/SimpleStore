package com.ecwid.simplestore.feature.product.data.mapper

import com.ecwid.simplestore.core.mapper.OneParameterReverseMapper
import com.ecwid.simplestore.data.model.ProductEntityModel
import com.ecwid.simplestore.feature.product.domain.model.ProductModel
import javax.inject.Inject

class ProductMapper @Inject constructor() :
    OneParameterReverseMapper<ProductEntityModel, ProductModel> {

    override fun map(source: ProductEntityModel) = ProductModel(
        id = source.id,
        name = source.name,
        price = source.price,
        address = source.address,
        latitude = source.latitude,
        longitude = source.longitude,
        icon = source.icon
    )

    override fun reverseMap(source: ProductModel) = ProductEntityModel(
        id = source.id,
        name = source.name,
        price = source.price,
        address = source.address,
        latitude = source.latitude,
        longitude = source.longitude,
        icon = source.icon
    )
}