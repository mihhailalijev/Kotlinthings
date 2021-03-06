package com.example.kotlinthings

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.vk.sdk.VKSdk
import com.vk.sdk.api.VKError
import com.vk.sdk.VKAccessToken
import com.vk.sdk.VKCallback
import android.widget.Toast
import com.example.kotlinthings.gallery.GalleryActivity
import com.vk.sdk.VKScope

class MainActivity : AppCompatActivity() {

    private var scope = VKScope.PHOTOS

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(VKSdk.isLoggedIn()) openGalleryActivity()

    }

    fun login(view: View) { VKSdk.login(this, scope) }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (!VKSdk.onActivityResult(
                requestCode,
                resultCode,
                data,
                object : VKCallback<VKAccessToken> {
                    override fun onResult(res: VKAccessToken) {
                        Toast.makeText(baseContext.applicationContext, "Successfully logged in", Toast.LENGTH_SHORT).show()
                        openGalleryActivity()
                    }
                    override fun onError(error: VKError) {
                        Toast.makeText(baseContext.applicationContext, "There was an error, ${requestCode}", Toast.LENGTH_LONG).show()
                    }
                })
        ) {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun openGalleryActivity() {
        val intent = Intent(baseContext.applicationContext, GalleryActivity::class.java)
        finish()
        startActivity(intent)
    }
}
