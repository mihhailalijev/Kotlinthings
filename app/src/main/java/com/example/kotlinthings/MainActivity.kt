package com.example.kotlinthings

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.vk.sdk.VKSdk
import com.vk.sdk.util.VKUtil


import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        println("Test")

        val context = baseContext.applicationContext

       VKSdk.login(this)
    }

    fun login(view: View) {

        println(loginInput.text)
        println(passwordInput.text)

        val intent = Intent(this, GalleryActivity::class.java)
        startActivity(intent)


        val fingerprints = VKUtil.getCertificateFingerprint(this, this.packageName)

        for (fingerprint in fingerprints) {
            println("fingerprint: $fingerprint")
            this.getPackageName()
        }



    }

}
