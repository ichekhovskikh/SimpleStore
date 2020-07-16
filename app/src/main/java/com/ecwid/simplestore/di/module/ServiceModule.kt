package com.ecwid.simplestore.di.module

import com.ecwid.simplestore.data.service.FileService
import com.ecwid.simplestore.data.service.FileServiceImpl
import com.ecwid.simplestore.data.service.ProductService
import com.ecwid.simplestore.data.service.ProductServiceImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class ServiceModule {

    @Singleton
    @Binds
    abstract fun bindProductService(productService: ProductServiceImpl): ProductService

    @Singleton
    @Binds
    abstract fun bindFileService(fileService: FileServiceImpl): FileService
}
