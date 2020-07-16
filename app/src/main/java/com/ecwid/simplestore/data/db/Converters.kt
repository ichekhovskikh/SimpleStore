package com.ecwid.simplestore.data.db

import android.graphics.Bitmap
import androidx.room.TypeConverter
import com.ecwid.simplestore.core.tools.createBitmap
import com.ecwid.simplestore.core.tools.toBase64

object Converters {

    class BitmapTypeConverter {

        @TypeConverter
        fun toBitmap(value: String): Bitmap {
            return createBitmap(value)
        }

        @TypeConverter
        fun toString(value: Bitmap): String {
            return value.toBase64()
        }
    }
}