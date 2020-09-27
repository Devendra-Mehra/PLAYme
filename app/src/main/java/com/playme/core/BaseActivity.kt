package com.playme.core

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import com.playme.R
import com.playme.core.permissions.PermissionConstants
import com.playme.core.permissions.PermissionsListener

open class BaseActivity : AppCompatActivity() {

    private val listeners: MutableMap<Int, PermissionsListener> = HashMap()

    fun isPermissionGranted(@NonNull permissions: List<String>, context: Context): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (permission in permissions) {
                if (context.checkSelfPermission(permission) ==
                    PackageManager.PERMISSION_DENIED
                ) return false
            }
        }
        return true
    }

    fun requestPermissionsIfNotGranted(
        @NonNull permissions: List<String>, permissionsListener: PermissionsListener,
        requestCode: Int, activity: Activity
    ) {
        listeners[requestCode] = permissionsListener
        val deniedPermissions: MutableList<String> = ArrayList()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            var allPermissionGranted = true
            for (permission in permissions) {
                try {
                    if (activity.checkSelfPermission(permission) == PackageManager.PERMISSION_DENIED) {
                        allPermissionGranted = false
                        deniedPermissions.add(permission)
                    }
                } catch (exception: Exception) {
                    permissionsListener.onPermissionException(
                        exception.message ?: getString(R.string.permission_exception)
                    )
                }
            }
            if (!allPermissionGranted) {
                activity.requestPermissions(
                    PermissionConstants.listToArray(deniedPermissions),
                    requestCode
                )
            } else {
                permissionsListener.onPermissionGranted()
            }
        } else {
            permissionsListener.onPermissionGranted()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (listeners[requestCode] != null && listeners.containsKey(requestCode)) {
                var requestPermissionRationale = false
                val deniedPermissions: MutableList<String> = ArrayList()

                for (i in grantResults.indices) {
                    permissions[i].let {
                        if (checkSelfPermission(it) == PackageManager.PERMISSION_DENIED) {
                            deniedPermissions.add(it)
                            if (shouldShowRequestPermissionRationale(it)) {
                                requestPermissionRationale = true
                            }
                        }
                    }
                }

                if (deniedPermissions.isNotEmpty()) {
                    if (requestPermissionRationale) {
                        requestPermissionsIfNotGranted(
                            permissions = deniedPermissions,
                            permissionsListener = listeners[requestCode]!!,
                            activity = this,
                            requestCode = requestCode
                        )
                    } else {
                        listeners[requestCode]?.onRequestPermissionRationale()
                    }


                } else {
                    listeners[requestCode]?.onPermissionGranted()
                }
            }
        } else {
            listeners[requestCode]?.onPermissionGranted()
        }
    }

    override fun onDestroy() {
        listeners.clear()
        super.onDestroy()
    }
}