package com.example.kotlinthings.fullScreen

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
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

        mPager = findViewById(R.id.pager)
        mPager.adapter = fragmentAdapter


    }

    override fun onBackPressed() {
        if (mPager.currentItem == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed()
        } else {
            // Otherwise, select the previous step.
            mPager.currentItem = mPager.currentItem - 1
        }
    }

    fun closePreview(view: View) {
        finish()
    }

     inner class FullScreenPreviewFragmentAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
        override fun getCount(): Int = NUM_PAGES
        override fun getItem(position: Int): Fragment =
            ScreenSlidePageFragment()
    }
}


