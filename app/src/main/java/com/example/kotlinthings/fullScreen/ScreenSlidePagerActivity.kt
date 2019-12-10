package com.example.kotlinthings.fullScreen

import android.os.Bundle
import android.provider.ContactsContract
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.kotlinthings.Photos
import com.example.kotlinthings.fullScreen.FullScreenPreviewFragmentAdapter
import com.example.kotlinthings.R

class ScreenSlidePagerActivity : FragmentActivity() {

    private lateinit var mPager: ViewPager



    var savedInstance : Bundle? = null
    private val NUM_PAGES = 5

    val fragmentAdapter =
        com.example.kotlinthings.fullScreen.FullScreenPreviewFragmentAdapter(
            supportFragmentManager
        )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        savedInstance = savedInstanceState
        setContentView(R.layout.activity_full_screen_preview)

        val url = intent.getStringExtra("id")

        mPager = findViewById(R.id.pager)
        mPager.adapter = fragmentAdapter


        mPager.setCurrentItem(Photos.INSTANCE.getPosition(url))
    }



    fun closePreview(view: View) {
        finish()
    }
}


