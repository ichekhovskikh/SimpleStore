package com.ecwid.simplestore.core.adapter.delegate

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ecwid.simplestore.R
import com.ecwid.simplestore.core.adapter.item.AdapterItemModel
import com.ecwid.simplestore.core.adapter.item.EmptyAdapterItemModel
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_empty.*

class EmptyItemAdapterDelegate :
    AbsListItemAdapterDelegate<EmptyAdapterItemModel, AdapterItemModel, EmptyItemAdapterDelegate.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.item_empty,
            parent,
            false
        )
    )

    override fun isForViewType(
        item: AdapterItemModel,
        items: List<AdapterItemModel>,
        position: Int
    ): Boolean = item is EmptyAdapterItemModel

    override fun onBindViewHolder(
        item: EmptyAdapterItemModel,
        viewHolder: ViewHolder,
        payloads: List<Any>
    ) {
        viewHolder.bind(item)
    }

    class ViewHolder(
        override val containerView: View
    ) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(item: EmptyAdapterItemModel) {
            tvMessage.text = item.message
        }
    }
}