package com.playme.core.permissions

import android.Manifest
import androidx.annotation.NonNull


object PermissionConstants {

    const val READ_EXTERNAL_STORAGE: String = Manifest.permission.READ_EXTERNAL_STORAGE
    const val WRITE_EXTERNAL_STORAGE: String = Manifest.permission.WRITE_EXTERNAL_STORAGE
    const val READ_EXTERNAL_STORAGE_REQUEST_CODE: Int = 100

    fun listToArray(permissionList: List<String>): Array<String> {
        return permissionList.toTypedArray()
    }

    fun getPermissions(@NonNull permissionName: String): List<String>? {
        val permissions: MutableList<String> = ArrayList()
        return when (permissionName) {
            READ_EXTERNAL_STORAGE -> {
                permissions.add(READ_EXTERNAL_STORAGE)
                permissions.add(WRITE_EXTERNAL_STORAGE)
                permissions
            }
            else -> {
                null
            }
        }
    }


    fun getPermissionsRequestCode(permissionName: String): Int {
        return when (permissionName) {
            READ_EXTERNAL_STORAGE -> {
                READ_EXTERNAL_STORAGE_REQUEST_CODE
            }
            else -> {
                -1
            }
        }
    }


}