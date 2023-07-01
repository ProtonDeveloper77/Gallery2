package com.example.protonGallery.permission

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.protonGallery.R


class PermissionManager private constructor() {
    companion object {
        fun getStorageLauncher(activity: ComponentActivity): ActivityResultLauncher<String> {
            return activity.registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
                if (isGranted) {
                    val dialogBuilder = AlertDialog.Builder(activity.applicationContext)
                        .setTitle(activity.getString(R.string.camera_permission_title))
                        .setMessage(activity.getString(R.string.storage_permission_message))
                        .setPositiveButton(activity.getString(R.string.grant_permission)) { dialog: DialogInterface, _: Int ->
                            dialog.dismiss()
                            ActivityCompat.requestPermissions(
                                activity,
                                arrayOf(PermissionUtil.READ_STORAGE),
                                PermissionUtil.READ_STORAGE_PERMISSION_REQUEST_CODE
                            )
                        }
                        .setNegativeButton(activity.getString(R.string.permission_deny)) { dialog: DialogInterface, _: Int ->
                            dialog.dismiss()
                            activity.finish()
                        }
                    val dialog = dialogBuilder.create()
                    dialog.show()
                }
            }
        }

        public fun getCameraLauncher(activity: ComponentActivity): ActivityResultLauncher<String> {
            return activity.registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
                if (isGranted) {
                    PermissionManager.getCameraLauncher(activity)
                        .launch(PermissionUtil.CAMERA_PERMISSION)
                } else {
                    AlertDialog.Builder(activity)
                        .setTitle(activity.getString(R.string.camera_permission_title))
                        .setMessage(activity.getString(R.string.camera_permission_message))
                        .setPositiveButton(activity.getString(R.string.grant_permission)) { _, _ ->
                            // Request camera permission
                            PermissionManager.getCameraLauncher(activity)
                                .launch(PermissionUtil.CAMERA_PERMISSION)
                        }
                        .setNegativeButton(activity.getString(R.string.permission_deny)) { _, _ ->
                            Toast.makeText(
                                activity,
                                activity.getString(R.string.camera_denied),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        .setCancelable(false)
                        .show()
                }
            }

        }

        public fun checkPermission(context: Context, permission: String): Boolean {
            return ContextCompat.checkSelfPermission(
                context, permission
            ) == PermissionUtil.PERMISSION_GRANTED
        }

        var permissionManager = PermissionManager
    }
}