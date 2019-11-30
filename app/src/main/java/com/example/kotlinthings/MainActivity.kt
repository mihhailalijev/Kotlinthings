package com.example.kotlinthings

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.vk.sdk.VKSdk
import com.vk.sdk.util.VKUtil


import kotlinx.android.synthetic.main.activity_main.*
import com.vk.sdk.api.VKError
import com.vk.sdk.VKAccessToken
import com.vk.sdk.VKCallback
import androidx.core.app.ComponentActivity
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.widget.Toast
import com.vk.sdk.VKScope


class MainActivity : AppCompatActivity() {

    // Authorization scope, to get photos
  //  private var scope = arrayOf<String>(VKScope.PHOTOS)

    private var scope = VKScope.PHOTOS
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    fun login(view: View) {

        // Triggers VK's login activity
        // This will trigger "onActivityResult"
        VKSdk.login(this, scope)



    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (!VKSdk.onActivityResult(
                requestCode,
                resultCode,
                data,
                object : VKCallback<VKAccessToken> {
                    override fun onResult(res: VKAccessToken) {
                        // on Authorization success
                        Toast.makeText(baseContext.applicationContext, "Successfully logged in", Toast.LENGTH_LONG).show()

                        // Open Gallery activity
                        val intent = Intent(baseContext.applicationContext, GalleryActivity::class.java)
                        startActivity(intent)
                    }
                    override fun onError(error: VKError) {
                        // On Authorization problem shows error code
                        Toast.makeText(baseContext.applicationContext, "There was an error, error code: $resultCode", Toast.LENGTH_LONG).show()
                    }
                })
        ) {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}
