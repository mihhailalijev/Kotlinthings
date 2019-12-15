package com.example.kotlinthings.fullScreen

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.viewpager.widget.ViewPager
import com.example.kotlinthings.Photos
import com.example.kotlinthings.R
import kotlinx.android.synthetic.main.activity_full_screen_preview.*
import com.squareup.picasso.Picasso

class ScreenSlidePagerActivity : FragmentActivity() {

    private lateinit var mPager: ViewPager
    var savedInstance : Bundle? = null
    val fragmentAdapter = FullScreenPreviewFragmentAdapter( supportFragmentManager)
    var imageUrl = "EMPTY DEFAULT LINK"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        savedInstance = savedInstanceState
        setContentView(R.layout.activity_full_screen_preview)

        imageUrl = intent.getStringExtra("id")

        mPager = findViewById(R.id.pager)
        mPager.adapter = fragmentAdapter

        var currentItemPos = Photos.ORIGSIZE.getPosition(imageUrl)
        mPager.setCurrentItem(currentItemPos)
        positionLabel.text = "${currentItemPos+1} / ${Photos.ORIGSIZE.count()}"

        mPager.addOnPageChangeListener(object :ViewPager.OnPageChangeListener {

        override fun onPageScrolled(position: Int, positionOffset: Float,positionOffsetPixels: Int) {
            imageUrl = Photos.ORIGSIZE.get(position)
        }

        override fun onPageSelected(position: Int) {
           imageUrl = Photos.ORIGSIZE.get(position)
            Log.i("IMAGE", "IMAGEURL: $imageUrl")
            positionLabel.text = "${position+1} / ${Photos.ORIGSIZE.count()}"
        }

        override fun onPageScrollStateChanged(state: Int) {}
        })
    }

    fun closePreview(view: View) { finish() }

    fun saveButtonClick(item: MenuItem) {

        PermissionManager(this).makeRequest()

        var bitmap : Bitmap
        val url = imageUrl

        Thread(Runnable {
            bitmap = Picasso.get().load(url).get()
            FileManager(this).saveFile(bitmap, false)
        }).start()

        Toast.makeText(this, "Photo saved!", Toast.LENGTH_LONG).show()
    }

    fun shareButtonClick(item: MenuItem) {

        PermissionManager(this).makeRequest()

        var bitmap : Bitmap
        val url = imageUrl

        Thread(Runnable {
            bitmap = Picasso.get().load(url).get()
            FileManager(this).saveFile(bitmap, true)
        }).start()
    }

    fun showPhotoPopup(view: View) {
        val popup = PopupMenu(this, view)
        val inflater: MenuInflater = popup.getMenuInflater()
        inflater.inflate(R.menu.photomenu, popup.getMenu())
        popup.show()
    }
}


