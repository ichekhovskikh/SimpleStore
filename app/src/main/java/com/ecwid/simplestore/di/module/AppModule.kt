package com.ecwid.simplestore.di.module

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.ecwid.simplestore.SimpleStoreApplication
import com.ecwid.simplestore.core.navigation.Navigator
import com.ecwid.simplestore.core.navigation.NavigatorImpl
import com.ecwid.simplestore.core.provider.DispatcherProvider
import com.ecwid.simplestore.core.provider.DispatcherProviderImpl
import com.ecwid.simplestore.core.provider.AddressProvider
import com.ecwid.simplestore.core.provider.AddressProviderImpl
import com.ecwid.simplestore.di.tools.ViewModelFactory
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class AppModule {

    @Singleton
    @Binds
    abstract fun bindContext(application: SimpleStoreApplication): Context

    @Singleton
    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Singleton
    @Binds
    abstract fun bindDispatcherProvider(
        dispatcherProvider: DispatcherProviderImpl
    ): DispatcherProvider

    @Singleton
    @Binds
    abstract fun bindNavigator(navigator: NavigatorImpl): Navigator

    @Singleton
    @Binds
    abstract fun bindAddressProvider(addressService: AddressProviderImpl): AddressProvider
}
