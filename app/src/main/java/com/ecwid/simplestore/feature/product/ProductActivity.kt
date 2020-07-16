package com.ecwid.simplestore.feature.product

import com.ecwid.simplestore.R
import com.ecwid.simplestore.core.activity.BaseActivity
import com.ecwid.simplestore.feature.product.screen.list.presentation.ListProductFragment

class ProductActivity : BaseActivity() {
    override val layoutId = R.layout.activity_container
    override val baseFragment = ListProductFragment()
}
