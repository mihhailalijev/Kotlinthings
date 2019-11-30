package com.example.kotlinthings

import android.util.Log
import com.vk.sdk.VKSdk
import kotlin.math.log
import com.vk.sdk.VKAccessToken
import com.vk.sdk.VKAccessTokenTracker
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T



class Application : android.app.Application() {

    var isUserLoggedIn = false

    override fun onCreate() {
        super.onCreate()

        vkAccessTokenTracker.startTracking()
        VKSdk.initialize(this)

        isUserLoggedIn = VKSdk.isLoggedIn()

        Log.i("SDKdebug", "is User already logged in: $isUserLoggedIn")

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