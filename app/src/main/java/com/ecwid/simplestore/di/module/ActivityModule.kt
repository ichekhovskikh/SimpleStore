package com.ecwid.simplestore.di.module

import com.ecwid.simplestore.feature.product.ProductActivity
import com.ecwid.simplestore.feature.product.di.ProductActivityModule
import com.ecwid.simplestore.feature.product.di.ProductActivityScope
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @ProductActivityScope
    @ContributesAndroidInjector(modules = [ProductActivityModule::class])
    abstract fun productActivity(): ProductActivity
}
