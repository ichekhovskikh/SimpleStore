package com.ecwid.simplestore.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.ecwid.simplestore.data.model.ProductEntityModel

@Dao
abstract class ProductDao {

    @Query("SELECT * FROM product WHERE id = :id")
    abstract fun getByIdLive(id: Long): LiveData<ProductEntityModel>

    @Query("SELECT * FROM product")
    abstract fun getAll(): List<ProductEntityModel>

    @Query("SELECT * FROM product WHERE product.name GLOB '*' || :name || '*'")
    abstract fun getAllLive(name: String?): LiveData<List<ProductEntityModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun add(product: ProductEntityModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun add(products: List<ProductEntityModel>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    abstract fun update(product: ProductEntityModel)

    @Query("DELETE FROM product WHERE id = :id")
    abstract fun removeById(id: Long)

    @Query("DELETE FROM product")
    abstract fun removeAll()

    @Transaction
    open fun updateAll(products: List<ProductEntityModel>) {
        removeAll()
        add(products)
    }
}