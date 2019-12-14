package com.example.kotlinthings.fullScreen

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class PermissionManager(private val activity: Activity) : Activity() {
    private val requestWriteDisk = 0

    fun makeRequest() {

        if (ContextCompat.checkSelfPermission(activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(activity,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                requestWriteDisk)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int,permissions: Array<String>,grantResults: IntArray) {
        when (requestWriteDisk) {
            requestWriteDisk -> {

                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED))
                {
                    Toast.makeText(activity, "Permission granted", Toast.LENGTH_LONG).show()
                }
                else { Toast.makeText(activity, "Permission denied", Toast.LENGTH_LONG).show() }

                return
            }
        }
    }


}