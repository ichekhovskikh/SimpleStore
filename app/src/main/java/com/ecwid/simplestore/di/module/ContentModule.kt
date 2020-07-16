package com.ecwid.simplestore.di.module

import android.content.ContentResolver
import android.content.Context
import android.content.res.Resources
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ContentModule {

    @Singleton
    @Provides
    fun provideResources(context: Context): Resources = context.resources

    @Singleton
    @Provides
    fun provideContentResolver(context: Context): ContentResolver = context.contentResolver
}
