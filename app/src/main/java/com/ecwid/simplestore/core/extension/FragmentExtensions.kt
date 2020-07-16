package com.ecwid.simplestore.core.extension

import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.ecwid.simplestore.core.tools.*

const val PICK_IMAGE = 1
const val CHOOSE_FILE = 2
const val CREATE_FILE = 3

inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction?) {
    beginTransaction().func()?.commit()
}

fun Fragment.tryOpenGallery(requestPermissions: Boolean = true) {
    val activity = activity ?: return
    when {
        hasStoragePermissions(activity) -> {
            val intent = Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.INTERNAL_CONTENT_URI
            )
            startActivityForResult(intent, PICK_IMAGE)
        }
        requestPermissions -> {
            requestStoragePermissions(
                this,
                OPEN_GALLERY_PERMISSIONS_REQUEST_CODE
            )
        }
    }
}

fun Fragment.tryOpenFileChooser(requestPermissions: Boolean = true) {
    val activity = activity ?: return
    when {
        hasStoragePermissions(activity) -> {
            val intent = Intent(
                Intent.ACTION_OPEN_DOCUMENT,
                MediaStore.Images.Media.INTERNAL_CONTENT_URI
            )
            startActivityForResult(Intent.createChooser(intent, null), CHOOSE_FILE)
        }
        requestPermissions -> {
            requestStoragePermissions(
                this,
                OPEN_FILE_CHOOSER_PERMISSIONS_REQUEST_CODE
            )
        }
    }
}

fun Fragment.tryOpenFileCreationChooser(name: String, requestPermissions: Boolean = true) {
    val activity = activity ?: return
    when {
        hasStoragePermissions(activity) -> {
            val intent = Intent(Intent.ACTION_CREATE_DOCUMENT).apply {
                addCategory(Intent.CATEGORY_OPENABLE)
                type = "text/csv"
                putExtra(Intent.EXTRA_TITLE, name)
            }
            startActivityForResult(Intent.createChooser(intent, null), CREATE_FILE)
        }
        requestPermissions -> {
            requestStoragePermissions(
                this,
                CREATE_FILE_CHOOSER_PERMISSIONS_REQUEST_CODE
            )
        }
    }
}

fun Fragment.openMap(
    latitude: Double?,
    longitude: Double?,
    description: String? = null
) {
    val mapIntent = Intent(Intent.ACTION_VIEW).apply {
        data = Uri.parse("geo:0,0?q=$latitude,$longitude($description)")
    }
    startActivity(mapIntent)
}