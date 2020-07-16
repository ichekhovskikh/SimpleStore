package com.ecwid.simplestore.feature.product.screen.update.di

import androidx.lifecycle.ViewModel
import com.ecwid.simplestore.R
import com.ecwid.simplestore.core.mapper.OneParameterMapper
import com.ecwid.simplestore.core.provider.AddressProvider
import com.ecwid.simplestore.di.tools.ViewModelKey
import com.ecwid.simplestore.feature.product.domain.model.ProductModel
import com.ecwid.simplestore.feature.product.screen.update.presentation.UpdateProductViewModel
import com.ecwid.simplestore.feature.product.screen.update.domain.usecase.AddProductUseCase
import com.ecwid.simplestore.feature.product.screen.update.domain.usecase.GetProductByIdUseCase
import com.ecwid.simplestore.feature.product.screen.update.domain.usecase.RemoveProductByIdUseCase
import com.ecwid.simplestore.feature.product.screen.update.domain.usecase.UpdateProductUseCase
import com.ecwid.simplestore.feature.product.screen.update.presentation.UpdateFragmentParams
import com.ecwid.simplestore.feature.product.screen.update.presentation.UpdateProductFragment
import com.ecwid.simplestore.feature.product.screen.update.presentation.mapper.ProductViewItemMapper
import com.ecwid.simplestore.feature.product.screen.update.presentation.model.ProductViewItemModel
import com.ecwid.simplestore.feature.product.screen.update.presentation.strategy.CreateProductStrategy
import com.ecwid.simplestore.feature.product.screen.update.presentation.strategy.EditProductStrategy
import com.ecwid.simplestore.feature.product.screen.update.presentation.strategy.SaveProductStrategy
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import javax.inject.Named

@Module
class UpdateProductFragmentModule {

    @UpdateProductFragmentScope
    @Provides
    fun provideParams(fragment: UpdateProductFragment): UpdateFragmentParams? =
        fragment.getParams()

    @UpdateProductFragmentScope
    @Provides
    fun provideSaveProductStrategy(
        params: UpdateFragmentParams?,
        addressProvider: AddressProvider,
        addProductUseCase: AddProductUseCase,
        updateProductUseCase: UpdateProductUseCase
    ): SaveProductStrategy = when (params?.id == null) {
        true -> CreateProductStrategy(addressProvider, addProductUseCase)
        else -> EditProductStrategy(addressProvider, updateProductUseCase)
    }

    @UpdateProductFragmentScope
    @Provides
    @Named(SCREEN_TITLE)
    fun provideFragmentTitle(
        fragment: UpdateProductFragment,
        params: UpdateFragmentParams?
    ): String = when (params?.id == null) {
        true -> fragment.resources.getString(R.string.create_product_title)
        else -> fragment.resources.getString(R.string.edit_product_title)
    }

    @UpdateProductFragmentScope
    @Provides
    @Named(VIEW_TRANSITION_NAME)
    fun provideViewTransitionName(
        params: UpdateFragmentParams?
    ): String = params?.transitionName ?: ""

    @UpdateProductFragmentScope
    @Provides
    fun provideProductViewItemMapper(): OneParameterMapper<ProductModel, ProductViewItemModel> =
        ProductViewItemMapper()

    @UpdateProductFragmentScope
    @Provides
    @IntoMap
    @ViewModelKey(UpdateProductViewModel::class)
    fun provideViewModel(
        params: UpdateFragmentParams?,
        saveProductStrategy: SaveProductStrategy,
        getProductByIdUseCase: GetProductByIdUseCase,
        removeProductByIdUseCase: RemoveProductByIdUseCase,
        productViewItemMapper: OneParameterMapper<ProductModel, ProductViewItemModel>
    ): ViewModel = UpdateProductViewModel(
        params?.id,
        saveProductStrategy,
        getProductByIdUseCase,
        removeProductByIdUseCase,
        productViewItemMapper
    )

    companion object {
        const val SCREEN_TITLE = "screenTitle"
        const val VIEW_TRANSITION_NAME = "viewTransitionName"
    }
}
