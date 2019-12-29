package com.example.kotlinthings.gallery

import android.app.AlertDialog
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinthings.MainActivity
import com.example.kotlinthings.Photos
import com.example.kotlinthings.R
import com.example.kotlinthings.fullScreen.ScreenSlidePagerActivity
import com.example.kotlinthings.network.DownloadBitmapIntoImageView
import com.example.kotlinthings.network.Parser
import com.example.kotlinthings.network.Requests
import com.vk.sdk.VKSdk
import kotlinx.android.synthetic.main.activity_gallery.*

class GalleryActivity : AppCompatActivity() {
    private val parser = Parser()
    private val requests = Requests()

    lateinit var recyclerView: RecyclerView
    lateinit var scrollListener: ScrollListener
    lateinit var viewManager: GridLayoutManager

    data class User(var name: String, var lastName: String)

    private val viewAdapter =
        GalleryAdapter { id ->
            val intent =
                Intent(this, ScreenSlidePagerActivity::class.java)
            intent.putExtra("id", id)
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            startActivity(intent)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)

        showProfilePhoto()
        showUserInfo()

        if(resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            viewManager = GridLayoutManager(this, 6)
        } else {
            viewManager = GridLayoutManager(this, 4)
        }

        scrollListener =
            ScrollListener(viewManager) {

                if (Photos.THUMBNAILS.count() != 0 && Photos.THUMBNAILS.count() == viewAdapter.itemCount)
                    showGallery(viewAdapter.itemCount)
            }

        recyclerView = findViewById(R.id.galleryRecyclerView)
        with(recyclerView) {
            adapter = viewAdapter
            layoutManager = viewManager
            addOnScrollListener(scrollListener)

        }

        showGallery()
    }

    private fun showGallery(offset: Int = 0) {
        val (thumbNails, origins) = parser.parseUserPhotoCollection(
            requests.getUserPhotoCollection(
                offset
            )
        )

        Photos.THUMBNAILS.addAll(thumbNails)
        viewAdapter.addAll(thumbNails)
        Photos.ORIGSIZE.addAll(origins)
        scrollListener.dataFetched()
    }

    private fun showUserInfo() {
        val user = parser.parseUserInfo(requests.getUserInfo())
        nameView.text = user.name
        lastNameView.text = user.lastName
    }

    private fun showProfilePhoto() {
        val photoLink = parser.parseUserPhoto(requests.getUserPhoto())
        DownloadBitmapIntoImageView(
            photoLink,
            profileAvatar
        ).execute()
    }

    fun showGalleryPopup(view: View) {
        val popup = PopupMenu(this, view)
        val inflater: MenuInflater = popup.menuInflater
        inflater.inflate(R.menu.gallerymenu, popup.menu)
        popup.show()
    }

    fun onMenuItemClick(item: MenuItem) {
        val alertDialog: AlertDialog = AlertDialog.Builder(this).create()
        alertDialog.setTitle("Log Out?")
        alertDialog.setMessage("You will be logged out")

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Ok", { dialog, which -> logOut()  })
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Cancel", { dialog, which -> alertDialog.dismiss()  })
        alertDialog.show()
    }

    fun logOut() {
        VKSdk.logout()
        finish()
        Photos.ORIGSIZE.clearAllPhotos()
        Photos.THUMBNAILS.clearAllPhotos()

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)

        Toast.makeText(this, "Logged out", Toast.LENGTH_SHORT).show()
    }
}



