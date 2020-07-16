package com.ecwid.simplestore.data.model

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "product")
data class ProductEntityModel(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var name: String = "",
    var price: Long = 0,
    var address: String = "",
    val latitude: Double? = null,
    val longitude: Double? = null,
    var icon: Bitmap? = null
)