package com.example.kotlinthings.gallery

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinthings.*
import com.example.kotlinthings.fullScreen.ScreenSlidePagerActivity
import com.squareup.picasso.Picasso
import com.vk.sdk.VKSdk
import com.vk.sdk.api.*
import com.vk.sdk.api.VKRequest.VKRequestListener
import kotlinx.android.synthetic.main.activity_gallery.*
import org.json.JSONObject

class GalleryActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private val viewAdapter =
        GalleryAdapter { id ->
            val intent =
                Intent(this, ScreenSlidePagerActivity::class.java)
            intent.putExtra("id", id)
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            startActivity(intent)
        }
    private val viewManager = GridLayoutManager(this, 4)
    private val scrollListener =
        ScrollListener(viewManager) {
            getAndDisplayPhotos(viewAdapter.itemCount)
        }

    class User(name: String, lastName: String) {
        var name = name
        var lastName = lastName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)

        recyclerView = findViewById(R.id.galleryRecyclerView)
        with(recyclerView) {
            adapter = viewAdapter
            layoutManager = viewManager
            addOnScrollListener(scrollListener)

        }

        val btn: Button = findViewById(R.id.menuButton)
        registerForContextMenu(btn)
        getProfilePhoto()
        getAndShowUserInfo()

        if(Photos.THUMBNAILS.count() == 0 && Photos.ORIGSIZE.count() == 0) getAndDisplayPhotos() else Log.i("LOG", "Loading more photos")

    }

    override fun onDestroy() {
        super.onDestroy()
        Photos.ORIGSIZE.clearAllPhotos()
        Photos.THUMBNAILS.clearAllPhotos()
    }

    private fun getAndDisplayPhotos(offset: Int = 0) {

        val requestParameters = VKParameters.from(
            VKApiConst.ALBUM_ID, "wall",
            VKApiConst.COUNT, "50",
            VKApiConst.OFFSET, offset
        )

        val request = VKRequest("photos.getAll", requestParameters)

        request.executeWithListener(object : VKRequestListener() {
            override fun onComplete(response: VKResponse) {
                val (thumbNails, origins) = createPhotoLinkLists(response)
                displayGallery(origins,thumbNails)
            }
        })
    }

    private fun createPhotoLinkLists(response: VKResponse): Pair<List<String>, List<String>> {

            val thumbnails = mutableListOf<String>()
            val origins = mutableListOf<String>()

            val `object` = JSONObject(response.responseString)
            val responseObject = `object`.getJSONObject("response")
            val array = responseObject.getJSONArray("items")
            val photoSizes = mutableListOf("photo_130", "photo_604", "photo_807", "photo_1280", "photo_2560")

            for (i in 0 until array.length()) {

                val item = array.getJSONObject(i)

                // Orig. sizes
                for(photoSize in photoSizes) {
                    if(item.has(photoSize)) {
                        thumbnails.add( item[photoSize].toString())
                        break
                    }
                }

                // Thumbnails
                for(i in photoSizes.size-1 downTo 0) {
                    val photoSize = photoSizes[i]
                    if(item.has(photoSize)) {
                        origins.add( item[photoSize].toString())
                        break
                    }
                }
            }

        return Pair(thumbnails,origins)
    }
    private fun createUserObject(response: VKResponse): User {

        val `object` = JSONObject(response.responseString)
        val responseObject = `object`.getJSONObject("response")

        val name = responseObject["first_name"].toString()
        val lastName = responseObject["last_name"].toString()

        return User( name, lastName)
    }

    private fun displayGallery(photoLinkList: List<String>, thumbNailsList: List<String>) {
        viewAdapter.addAll(thumbNailsList)
        Photos.THUMBNAILS.addAll(thumbNailsList)
        Photos.ORIGSIZE.addAll(photoLinkList)
        scrollListener.dataFetched()
    }

    fun logOut() {
        VKSdk.logout()
        finish()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        Photos.ORIGSIZE.clearAllPhotos()
        Photos.THUMBNAILS.clearAllPhotos()
        Toast.makeText(this, "Logged out", Toast.LENGTH_LONG).show()
    }

    private fun getAndShowUserInfo() {
        val request = VKRequest("account.getProfileInfo")

        request.executeWithListener(object : VKRequestListener() {
            override fun onComplete(response: VKResponse) {
                //Do complete stuff
                showUserInfo(createUserObject(response))
            }
        })
    }

    private fun showUserInfo(user: User) {
        nameView.text = user.name
        lastNameView.text = user.lastName
    }

    fun showGalleryPopup(view: View) {
        val popup = PopupMenu(this, view)
        val inflater: MenuInflater = popup.getMenuInflater()
        inflater.inflate(R.menu.gallerymenu, popup.getMenu())
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

    private fun getProfilePhoto() {

        var requestParameters = VKParameters.from(
            VKApiConst.FIELDS,
            "photo_max_orig"
        )
        var request = VKRequest("users.get", requestParameters)

        request.executeWithListener(object : VKRequestListener() {
            override fun onComplete(response: VKResponse) {
                //Do complete stuff

                val resp = response.json.getJSONArray("response")
                val user = resp.getJSONObject(0)
                var photoLink = user.getString("photo_max_orig").toString()

                Picasso.get().load(photoLink).into(profileAvatar)
                }
        })
    }
}



