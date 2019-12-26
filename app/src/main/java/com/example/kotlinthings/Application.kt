package com.example.kotlinthings

import android.app.ActivityManager
import android.content.Context
import android.util.Log
import com.vk.sdk.VKAccessToken
import com.vk.sdk.VKAccessTokenTracker
import com.vk.sdk.VKSdk


class Application : android.app.Application() {

    override fun onCreate() {
        super.onCreate()

        vkAccessTokenTracker.startTracking()
        VKSdk.initialize(this)

        Log.i("SDKdebug", "is User already logged in: ${VKSdk.isLoggedIn()}")
    }

    var vkAccessTokenTracker: VKAccessTokenTracker = object : VKAccessTokenTracker() {
        override fun onVKAccessTokenChanged(oldToken: VKAccessToken?, newToken: VKAccessToken?) {
            if (newToken == null) {
                // VKAccessToken is invalid
                val activity = MainActivity()
                VKSdk.login(activity)
            }
        }
    }

}