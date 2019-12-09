package com.example.kotlinthings.FullScreen


import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.kotlinthings.R


class FullScreenPreview : FragmentActivity() {

    var savedInstance : Bundle? = null
    private val NUM_PAGES = 5

    private lateinit var mPager: ViewPager
    val fragmentAdapter =
        FullScreenPreviewFragmentAdapter(
            supportFragmentManager
        )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        savedInstance = savedInstanceState
        setContentView(R.layout.activity_full_screen_preview)

        val url = intent.getStringExtra("id")
     //   Picasso.get().load(url).into(fullScreenImage)
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

    private inner class ScreenSlidePagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
        override fun getCount(): Int = NUM_PAGES
        override fun getItem(position: Int): Fragment =
            ScreenSlidePageFragment()
    }
}


