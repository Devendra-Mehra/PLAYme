package com.playme.core.permissions

interface PermissionsListener {

    fun onPermissionGranted()

    fun onRequestPermissionRationale(isShowRequestPermissionRationale: Boolean)

    fun onPermissionException(exceptionMessage: String)
}