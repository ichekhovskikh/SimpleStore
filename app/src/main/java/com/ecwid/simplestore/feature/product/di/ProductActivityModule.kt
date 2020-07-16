package com.ecwid.simplestore.feature.product.di

import com.ecwid.simplestore.core.mapper.OneParameterReverseMapper
import com.ecwid.simplestore.data.model.ProductEntityModel
import com.ecwid.simplestore.feature.product.data.ProductRepositoryImpl
import com.ecwid.simplestore.feature.product.data.mapper.ProductMapper
import com.ecwid.simplestore.feature.product.domain.ProductRepository
import com.ecwid.simplestore.feature.product.domain.gateway.ProductGateway
import com.ecwid.simplestore.feature.product.domain.model.ProductModel
import com.ecwid.simplestore.feature.product.screen.update.presentation.UpdateProductFragment
import com.ecwid.simplestore.feature.product.screen.update.di.UpdateProductFragmentModule
import com.ecwid.simplestore.feature.product.screen.update.di.UpdateProductFragmentScope
import com.ecwid.simplestore.feature.product.screen.list.presentation.ListProductFragment
import com.ecwid.simplestore.feature.product.screen.list.di.ListProductFragmentModule
import com.ecwid.simplestore.feature.product.screen.list.di.ListProductFragmentScope
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ProductActivityModule {

    @ListProductFragmentScope
    @ContributesAndroidInjector(modules = [ListProductFragmentModule::class])
    abstract fun listProductFragment(): ListProductFragment

    @UpdateProductFragmentScope
    @ContributesAndroidInjector(modules = [UpdateProductFragmentModule::class])
    abstract fun createProductFragment(): UpdateProductFragment

    @ProductActivityScope
    @Binds
    abstract fun bindProductMapper(
        productMapper: ProductMapper
    ): OneParameterReverseMapper<ProductEntityModel, ProductModel>

    @ProductActivityScope
    @Binds
    abstract fun bindProductRepository(repository: ProductRepositoryImpl): ProductRepository

    @ProductActivityScope
    @Binds
    abstract fun bindProductGateway(repository: ProductRepository): ProductGateway
}
