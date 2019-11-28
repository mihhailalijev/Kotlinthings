package com.example.kotlinthings

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.vk.sdk.VKSdk


import kotlinx.android.synthetic.main.activity_main.*
import com.vk.sdk.api.VKError
import com.vk.sdk.VKAccessToken
import com.vk.sdk.VKCallback
import androidx.core.app.ComponentActivity
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import com.vk.sdk.WebView


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        println("Test")


     //   VKSdk.login(this)
    }

    fun login(view: View) {

        println(loginInput.text)
        println(passwordInput.text)

        val intent = Intent(this, GalleryActivity::class.java).apply {
           // startActivity(intent)
        }
        startActivity(intent)


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        println("VK - onActivityResult")
        if (!// Пользователь успешно авторизовался
            // Произошла ошибка авторизации (например, пользователь запретил авторизацию)
            VKSdk.onActivityResult(
                requestCode,
                resultCode,
                data,
                object : VKCallback<VKAccessToken> {
                    override fun onResult(res: VKAccessToken) {}
                    override fun onError(error: VKError) {}
                })
        ) {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}
