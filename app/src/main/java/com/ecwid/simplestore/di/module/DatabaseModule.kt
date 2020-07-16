package com.ecwid.simplestore.di.module

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import com.ecwid.simplestore.data.dao.ProductDao
import com.ecwid.simplestore.data.db.ApplicationDatabase
import com.ecwid.simplestore.data.db.Migrations
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideMigrations(): Array<Migration> = Migrations.get()

    @Singleton
    @Provides
    fun provideDatabase(
        context: Context,
        migrations: Array<Migration>
    ) = Room.databaseBuilder(
        context,
        ApplicationDatabase::class.java,
        ApplicationDatabase.DATABASE_NAME
    )
        .addMigrations(*migrations)
        .build()

    @Singleton
    @Provides
    fun provideProductDao(db: ApplicationDatabase): ProductDao = db.productDao
}