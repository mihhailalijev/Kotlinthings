package com.example.kotlinthings

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

open class GalleryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)
    }
}
