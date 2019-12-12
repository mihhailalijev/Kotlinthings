package com.example.kotlinthings.fullScreen

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.viewpager.widget.ViewPager
import com.example.kotlinthings.Photos
import com.example.kotlinthings.R
import kotlinx.android.synthetic.main.activity_full_screen_preview.*

class ScreenSlidePagerActivity : FragmentActivity() {

    private lateinit var mPager: ViewPager
    var savedInstance : Bundle? = null
    val fragmentAdapter = FullScreenPreviewFragmentAdapter( supportFragmentManager)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        savedInstance = savedInstanceState
        setContentView(R.layout.activity_full_screen_preview)

        val url = intent.getStringExtra("id")

        mPager = findViewById(R.id.pager)
        mPager.adapter = fragmentAdapter

        val currentItemPos = Photos.INSTANCE.getPosition(url)
        mPager.setCurrentItem(currentItemPos)

        positionLabel.text = "${currentItemPos+1} / ${Photos.INSTANCE.count()}"

        mPager.addOnPageChangeListener(object :ViewPager.OnPageChangeListener {

        override fun onPageScrolled(position: Int, positionOffset: Float,positionOffsetPixels: Int) {

        }

        override fun onPageSelected(position: Int) {
            positionLabel.text = "${position+1} / ${Photos.INSTANCE.count()}"
        }

        override fun onPageScrollStateChanged(state: Int) {

        }
        })
    }

    fun closePreview(view: View) {
        finish()
    }
}


