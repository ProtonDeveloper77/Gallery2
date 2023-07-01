package com.example.protonGallery.permission

import android.Manifest
import android.content.pm.PackageManager

class PermissionUtil {
    companion object{
        const val CAMERA_PERMISSION = Manifest.permission.CAMERA
        const val READ_STORAGE_PERMISSION_REQUEST_CODE = 102
        const val READ_STORAGE = Manifest.permission.READ_EXTERNAL_STORAGE
        const val PERMISSION_GRANTED = PackageManager.PERMISSION_GRANTED
    }
}