package com.example.kotlinthings.fullScreen

import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
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
import com.squareup.picasso.Target
import java.lang.Exception

class ScreenSlidePagerActivity : FragmentActivity() {

    private lateinit var mPager: ViewPager
    private val fragmentAdapter = FullScreenPreviewFragmentAdapter(supportFragmentManager)
    var savedInstance : Bundle? = null
    lateinit var imageUrl: String
    var isShared = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

       // security image (its not)
       if(Photos.ORIGSIZE.count() == 0) {
           finish()
            Toast.makeText(this, "There are no photos loaded", Toast.LENGTH_LONG).show()

        }

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
            positionLabel.text = "${position+1} / ${Photos.ORIGSIZE.count()}"
        }

        override fun onPageScrollStateChanged(state: Int) {}
        })
    }

    fun closePreview(view: View) { finish() }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            0 -> {

                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED))
                {
                    Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show()

                    val url = imageUrl
                    Picasso.get().load(url).into(object: Target {

                        private val _context = this@ScreenSlidePagerActivity
                        override fun onPrepareLoad(placeHolderDrawable: Drawable?) {}
                        override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {Toast.makeText(_context, "Failed to get image",  Toast.LENGTH_LONG).show()}

                        override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                            FileManager(_context).saveFile(bitmap!!, isShared)
                        }
                    })

                }
                else {
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                }
                return
            }
        }
    }

    fun saveOrShareButtonClick(item: MenuItem) {

        isShared = when (item.itemId) {
            R.id.shareButton -> true
            else -> false
        }

        if(!PermissionManager(this).getPermissionStatus()) {
            Toast.makeText(this, "There is no permission",  Toast.LENGTH_LONG).show()
            PermissionManager(this).makeRequest()
            return
        }


        val url = imageUrl
        Picasso.get().load(url).into(object: Target {
            private val _context = this@ScreenSlidePagerActivity
            override fun onPrepareLoad(placeHolderDrawable: Drawable?) {}
            override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {Toast.makeText(_context, "Failed to get image",  Toast.LENGTH_LONG).show()}

            override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                    FileManager(_context).saveFile(bitmap!!, isShared)
            }
        })
    }

    fun showPhotoMenuPopup(view: View) {
        val popup = PopupMenu(this, view)
        val inflater: MenuInflater = popup.getMenuInflater()
        inflater.inflate(R.menu.photomenu, popup.getMenu())
        popup.show()
    }
}


