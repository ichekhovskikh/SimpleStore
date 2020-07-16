package com.ecwid.simplestore.core.tools

import android.content.ContentResolver
import android.content.Context
import android.graphics.*
import android.util.Base64
import java.io.ByteArrayOutputStream
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Build.VERSION_CODES.P
import android.provider.MediaStore
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur

fun Bitmap.toBase64(): String {
    val byteArrayOutputStream = ByteArrayOutputStream()
    compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
    val byteArray = byteArrayOutputStream.toByteArray()
    return Base64.encodeToString(byteArray, Base64.DEFAULT)
}

fun createBitmap(base64: String): Bitmap {
    val encodeByte = Base64.decode(base64, Base64.DEFAULT)
    return BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.size)
}

fun Bitmap?.isSame(bitmap: Bitmap?): Boolean {
    if (this == bitmap) return true
    return this?.sameAs(bitmap) ?: false
}

fun getSupportBitmap(contentResolver: ContentResolver, imageUri: Uri): Bitmap {
    val bitmap = when {
        Build.VERSION.SDK_INT >= P -> {
            val source = ImageDecoder.createSource(contentResolver, imageUri)
            ImageDecoder.decodeBitmap(source)
        }
        else -> {
            MediaStore.Images.Media.getBitmap(contentResolver, imageUri)
        }
    }
    return Bitmap.createScaledBitmap(bitmap, 200, 200, false)
}

fun Bitmap.blur(context: Context, radius: Float = 10f): Bitmap =
    copy(Bitmap.Config.ARGB_8888, true).apply {
        val renderScript = RenderScript.create(context)
        val input = Allocation.createFromBitmap(renderScript, this)
        val output = Allocation.createTyped(renderScript, input.type)
        val script = ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript))
        script.setRadius(radius)
        script.setInput(input)
        script.forEach(output)
        output.copyTo(this)
    }
