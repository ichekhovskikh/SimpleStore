package com.ecwid.simplestore.core.tools

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

const val OPEN_GALLERY_PERMISSIONS_REQUEST_CODE = 220
const val OPEN_FILE_CHOOSER_PERMISSIONS_REQUEST_CODE = 221
const val CREATE_FILE_CHOOSER_PERMISSIONS_REQUEST_CODE = 222

fun hasStoragePermissions(activity: Activity) =
    ContextCompat.checkSelfPermission(
        activity,
        READ_EXTERNAL_STORAGE
    ) == PackageManager.PERMISSION_GRANTED
            && ContextCompat.checkSelfPermission(
        activity,
        WRITE_EXTERNAL_STORAGE
    ) == PackageManager.PERMISSION_GRANTED

fun requestStoragePermissions(fragment: Fragment, requestCode: Int) {
    fragment.requestPermissions(
        arrayOf(READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE),
        requestCode
    )
}