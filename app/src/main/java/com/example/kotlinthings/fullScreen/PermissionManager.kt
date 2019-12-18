package com.example.kotlinthings.fullScreen

import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.app.Activity
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.util.Log
import androidx.core.app.ActivityCompat.*

class PermissionManager(private val activity: Activity){
    private val requestWriteDisk = 0

    fun makeRequest(){

        if (checkSelfPermission(activity, WRITE_EXTERNAL_STORAGE) != PERMISSION_GRANTED) {

            requestPermissions(activity, arrayOf(WRITE_EXTERNAL_STORAGE),
                requestWriteDisk)
        }
    }


    fun getPermissionStatus(): Boolean {
        return checkSelfPermission(activity, WRITE_EXTERNAL_STORAGE) == PERMISSION_GRANTED
    }
}