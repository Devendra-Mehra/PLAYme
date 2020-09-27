package com.playme.core.permissions

interface PermissionsListener {

    fun onPermissionGranted()

    fun onRequestPermissionRationale()

    fun onPermissionException(exceptionMessage: String)
}