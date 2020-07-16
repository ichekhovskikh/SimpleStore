package com.ecwid.simplestore.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ecwid.simplestore.data.dao.ProductDao
import com.ecwid.simplestore.data.db.Converters.BitmapTypeConverter
import com.ecwid.simplestore.data.model.ProductEntityModel

@Database(
    entities = [
        ProductEntityModel::class
    ],
    version = 1
)
@TypeConverters(
    BitmapTypeConverter::class
)
abstract class ApplicationDatabase : RoomDatabase() {
    abstract val productDao: ProductDao

    companion object {
        const val DATABASE_NAME = "simple_store.db"
    }
}
