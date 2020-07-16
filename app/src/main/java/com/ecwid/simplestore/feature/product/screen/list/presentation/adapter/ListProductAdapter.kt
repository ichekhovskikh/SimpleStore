package com.ecwid.simplestore.feature.product.screen.list.presentation.adapter

import android.view.View
import com.ecwid.simplestore.core.adapter.delegate.EmptyItemAdapterDelegate
import com.ecwid.simplestore.core.adapter.item.AdapterItemModel
import com.ecwid.simplestore.feature.product.screen.list.presentation.adapter.differ.ProductDiffCallback
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import javax.inject.Inject

class ListProductAdapter @Inject constructor(
    differ: ProductDiffCallback,
    listener: ListProductListener
) : AsyncListDifferDelegationAdapter<AdapterItemModel>(differ) {

    init {
        delegatesManager.addDelegate(ProductItemAdapterDelegate(listener))
        delegatesManager.addDelegate(EmptyItemAdapterDelegate())
    }

    interface ListProductListener {
        fun onProductClick(view: View, id: Long)
        fun onShowOnMapClick(name: String, latitude: Double?, longitude: Double?)
    }
}