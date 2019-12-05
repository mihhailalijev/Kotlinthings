package com.example.kotlinthings

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_full_screen_preview.*

class FullScreenPreview : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_screen_preview)

        val url = intent.getStringExtra("id")

        Picasso.get().load(url).into(fullScreenImage)
    }

    fun closePreview(view: View) {
        val intent = Intent(baseContext.applicationContext,
            GalleryActivity::class.java)
        finish()
        startActivity(intent)
    }
}
