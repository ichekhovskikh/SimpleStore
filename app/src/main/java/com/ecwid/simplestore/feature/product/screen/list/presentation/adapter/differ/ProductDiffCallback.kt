package com.ecwid.simplestore.feature.product.screen.list.presentation.adapter.differ

import androidx.recyclerview.widget.DiffUtil
import com.ecwid.simplestore.core.adapter.item.AdapterItemModel
import com.ecwid.simplestore.core.adapter.item.EmptyAdapterItemModel
import com.ecwid.simplestore.core.tools.isSame
import com.ecwid.simplestore.feature.product.screen.list.presentation.model.ProductAdapterItemModel
import javax.inject.Inject

class ProductDiffCallback @Inject constructor() : DiffUtil.ItemCallback<AdapterItemModel>() {

    override fun areItemsTheSame(
        oldItem: AdapterItemModel,
        newItem: AdapterItemModel
    ): Boolean = when {
        oldItem is ProductAdapterItemModel && newItem is ProductAdapterItemModel -> {
            oldItem.id == newItem.id
        }
        oldItem is EmptyAdapterItemModel && newItem is EmptyAdapterItemModel -> {
            true
        }
        else -> false
    }

    override fun areContentsTheSame(
        oldItem: AdapterItemModel,
        newItem: AdapterItemModel
    ): Boolean = when {
        oldItem is ProductAdapterItemModel && newItem is ProductAdapterItemModel -> {
            oldItem.name == newItem.name
                    && oldItem.address == newItem.address
                    && oldItem.price == newItem.price
                    && oldItem.icon.isSame(newItem.icon)
        }
        oldItem is EmptyAdapterItemModel && newItem is EmptyAdapterItemModel -> {
            oldItem.message == newItem.message
        }
        else -> false
    }


    override fun getChangePayload(
        oldItem: AdapterItemModel,
        newItem: AdapterItemModel
    ): Any? {
        if (oldItem !is ProductAdapterItemModel || newItem !is ProductAdapterItemModel) {
            return super.getChangePayload(oldItem, newItem)
        }
        val isChangedName = oldItem.name != newItem.name
        val isChangedPrice = oldItem.price != newItem.price
        val isChangedAddress = oldItem.address != newItem.address
        val isChangedIcon = oldItem.icon.isSame(newItem.icon)
        return if (isChangedName || isChangedPrice || isChangedAddress || isChangedIcon) {
            ProductChangePayload(
                isChangedName,
                isChangedPrice,
                isChangedAddress,
                isChangedIcon
            )
        } else super.getChangePayload(oldItem, newItem)
    }
}