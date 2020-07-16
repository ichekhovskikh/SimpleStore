package com.ecwid.simplestore.di

import com.ecwid.simplestore.SimpleStoreApplication
import com.ecwid.simplestore.di.module.*
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        AppModule::class,
        ContentModule::class,
        DatabaseModule::class,
        ServiceModule::class,
        ActivityModule::class
    ]
)
interface AppComponent : AndroidInjector<SimpleStoreApplication> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: SimpleStoreApplication): Builder

        fun build(): AppComponent
    }
}
