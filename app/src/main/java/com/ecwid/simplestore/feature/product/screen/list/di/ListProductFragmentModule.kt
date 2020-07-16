package com.ecwid.simplestore.feature.product.screen.list.di

import android.content.res.Resources
import androidx.lifecycle.ViewModel
import com.ecwid.simplestore.core.mapper.OneParameterMapper
import com.ecwid.simplestore.di.tools.ViewModelKey
import com.ecwid.simplestore.feature.product.domain.model.ProductModel
import com.ecwid.simplestore.feature.product.screen.list.domain.usecase.ExportProductsUseCase
import com.ecwid.simplestore.feature.product.screen.list.presentation.ListProductViewModel
import com.ecwid.simplestore.feature.product.screen.list.domain.usecase.GetProductAllUseCase
import com.ecwid.simplestore.feature.product.screen.list.domain.usecase.ImportProductsUseCase
import com.ecwid.simplestore.feature.product.screen.list.presentation.ListProductFragment
import com.ecwid.simplestore.feature.product.screen.list.presentation.adapter.ListProductAdapter
import com.ecwid.simplestore.feature.product.screen.list.presentation.adapter.differ.ProductDiffCallback
import com.ecwid.simplestore.feature.product.screen.list.presentation.mapper.ProductAdapterItemMapper
import com.ecwid.simplestore.feature.product.screen.list.presentation.model.ProductAdapterItemModel
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
class ListProductFragmentModule {

    @ListProductFragmentScope
    @Provides
    fun provideProductAdapterItemMapper(): OneParameterMapper<ProductModel, ProductAdapterItemModel> =
        ProductAdapterItemMapper()

    @ListProductFragmentScope
    @Provides
    fun provideListProductListener(
        fragment: ListProductFragment
    ): ListProductAdapter.ListProductListener = fragment

    @ListProductFragmentScope
    @Provides
    fun provideListProductAdapter(
        differ: ProductDiffCallback,
        listener: ListProductAdapter.ListProductListener
    ): ListProductAdapter = ListProductAdapter(differ, listener)

    @ListProductFragmentScope
    @Provides
    @IntoMap
    @ViewModelKey(ListProductViewModel::class)
    fun provideViewModel(
        resources: Resources,
        getProductAllUseCase: GetProductAllUseCase,
        importProductsUseCase: ImportProductsUseCase,
        exportProductsUseCase: ExportProductsUseCase,
        productAdapterItemMapper: OneParameterMapper<ProductModel, ProductAdapterItemModel>
    ): ViewModel = ListProductViewModel(
        resources,
        getProductAllUseCase,
        importProductsUseCase,
        exportProductsUseCase,
        productAdapterItemMapper
    )
}
