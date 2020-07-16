package com.ecwid.simplestore.feature.product.screen.list.presentation.adapter

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.ecwid.simplestore.R
import com.ecwid.simplestore.core.adapter.item.AdapterItemModel
import com.ecwid.simplestore.feature.product.screen.list.presentation.adapter.differ.ProductChangePayload
import com.ecwid.simplestore.feature.product.screen.list.presentation.model.ProductAdapterItemModel
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_product.*

class ProductItemAdapterDelegate(
    private val listener: ListProductAdapter.ListProductListener
) : AbsListItemAdapterDelegate<ProductAdapterItemModel, AdapterItemModel, ProductItemAdapterDelegate.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.item_product,
            parent,
            false
        ),
        listener
    )

    override fun isForViewType(
        item: AdapterItemModel,
        items: List<AdapterItemModel>,
        position: Int
    ): Boolean = item is ProductAdapterItemModel

    override fun onBindViewHolder(
        item: ProductAdapterItemModel,
        holder: ViewHolder,
        payloads: List<Any>
    ) {
        when (val payload = payloads.firstOrNull()) {
            is ProductChangePayload -> {
                if (payload.isChangedName) holder.setName(item.name)
                if (payload.isChangedPrice) holder.setPrice(item.price)
                if (payload.isChangedAddress) holder.setAddress(
                    item.address,
                    item.latitude,
                    item.longitude
                )
                if (payload.isChangedIcon) holder.setIcon(item.icon)
            }
            else -> holder.bind(item)
        }
    }

    class ViewHolder(
        override val containerView: View,
        private val listener: ListProductAdapter.ListProductListener
    ) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        private val resources = itemView.resources

        fun bind(item: ProductAdapterItemModel) {
            itemView.transitionName = item.id.toString()
            setupListeners(item)
            setName(item.name)
            setPrice(item.price)
            setAddress(item.address, item.latitude, item.longitude)
            setIcon(item.icon)
        }

        private fun setupListeners(item: ProductAdapterItemModel) {
            itemView.setOnClickListener {
                listener.onProductClick(itemView, item.id)
            }
            tvShowOnMap.setOnClickListener {
                listener.onShowOnMapClick(item.name, item.latitude, item.longitude)
            }
        }

        fun setName(name: String) {
            tvName.text = name
        }

        fun setPrice(price: Long) {
            tvPrice.text = resources.getString(R.string.ruble, price.toString())
        }

        fun setAddress(address: String, latitude: Double?, longitude: Double?) {
            val hasCoordinates = latitude != null && longitude != null
            tvAddress.text = when {
                hasCoordinates -> resources.getString(
                    R.string.product_address_with_coordinates,
                    address,
                    latitude,
                    longitude
                )
                else -> address
            }
            tvShowOnMap.isVisible = hasCoordinates

        }

        fun setIcon(icon: Bitmap?) {
            civIcon.setImageBitmap(icon)
        }
    }
}