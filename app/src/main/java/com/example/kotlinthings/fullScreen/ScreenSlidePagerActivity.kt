package com.example.kotlinthings.fullScreen

import android.os.Bundle
import android.view.View
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.FragmentActivity
import androidx.viewpager.widget.ViewPager
import com.example.kotlinthings.Photos
import com.example.kotlinthings.R
import kotlinx.android.synthetic.main.activity_full_screen_preview.*
import kotlinx.android.synthetic.main.fragment_full_screen_preview.*

class ScreenSlidePagerActivity : FragmentActivity() {

    private lateinit var mPager: ViewPager
    var savedInstance : Bundle? = null
    val fragmentAdapter = FullScreenPreviewFragmentAdapter( supportFragmentManager)
    var imageUrl = "EMPTY DEFAULT LINK"
    private val requestWriteDisk = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        savedInstance = savedInstanceState
        setContentView(R.layout.activity_full_screen_preview)

        imageUrl = intent.getStringExtra("id")

        mPager = findViewById(R.id.pager)
        mPager.adapter = fragmentAdapter

        val currentItemPos = Photos.ORIGSIZE.getPosition(imageUrl)
        mPager.setCurrentItem(currentItemPos)

        positionLabel.text = "${currentItemPos+1} / ${Photos.ORIGSIZE.count()}"

        mPager.addOnPageChangeListener(object :ViewPager.OnPageChangeListener {

        override fun onPageScrolled(position: Int, positionOffset: Float,positionOffsetPixels: Int) {

        }

        override fun onPageSelected(position: Int) {
            positionLabel.text = "${position+1} / ${Photos.ORIGSIZE.count()}"
        }

        override fun onPageScrollStateChanged(state: Int) {

        }
        })
    }

    fun closePreview(view: View) { finish() }

    fun shareImage(view: View) {
        var drawable = fullScreenImage.getDrawable()
        var bitmap = drawable.toBitmap()

        PermissionManager(this).makeRequest()

        FileManager(this).saveFile(bitmap)
    }
}


