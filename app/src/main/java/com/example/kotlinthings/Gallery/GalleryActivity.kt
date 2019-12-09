package com.example.kotlinthings.Gallery

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
import com.example.kotlinthings.FullScreen.FullScreenPreview
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
                Intent(this, FullScreenPreview::class.java)
            intent.putExtra("id", id)
            startActivity(intent)

        }
    private val viewManager = GridLayoutManager(this, 4)
    private val scrollListener =
        ScrollListener(viewManager) {
            Log.i(debugTag, "Scroll to Bottom called")
            getAndDisplayPhotos(viewAdapter.itemCount)
        }

    private val debugTag = "SDKdebug"
    var photoLinkList = mutableListOf<String>()

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

        val btn: Button = findViewById(R.id.btnShow)
        registerForContextMenu(btn)
        getProfilePhoto()
        getAndShowUserInfo()
        if (photoLinkList.count() == 0) getAndDisplayPhotos()
    }

    private fun getAndDisplayPhotos(offset: Int = 0) {

        var requestParameters = VKParameters.from(
            VKApiConst.ALBUM_ID, "wall",
            VKApiConst.COUNT, "50",
            VKApiConst.OFFSET, offset
        )

        var request = VKRequest("photos.getAll", requestParameters)

        request.executeWithListener(object : VKRequestListener() {
            override fun onComplete(response: VKResponse) {
                createPhotoLinkList(response)
                displayGallery(photoLinkList)
            }
        })
    }

    private fun createPhotoLinkList(response: VKResponse) {

            val `object` = JSONObject(response.responseString)
            val responseObject = `object`.getJSONObject("response")
            val array = responseObject.getJSONArray("items")

            // Clear the list, so there will be no duplicates
            photoLinkList.clear()

            for (i in 0 until array.length()) {

                // item = Photo object with id and link
                var item = array.getJSONObject(i)
                var link = item["photo_604"].toString() // Get link for photo

                photoLinkList.add(link)
            }
    }

    private fun createUserObject(response: VKResponse): User {

        val `object` = JSONObject(response.responseString)
        val responseObject = `object`.getJSONObject("response")

        var name = responseObject["first_name"].toString()
        var lastName = responseObject["last_name"].toString()

        return User(
            name,
            lastName
        )
    }

    private fun displayGallery(photoLinkList: List<String>) {
        Log.i(debugTag, "thread: ${Thread.currentThread().name}")
        viewAdapter.addAll(photoLinkList)
        scrollListener.dataFetched()
    }

    open fun logOut() {
        VKSdk.logout()
        finish()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        Toast.makeText(this, "Logged out", Toast.LENGTH_LONG).show()
    }

    private fun getAndShowUserInfo() {
        var request = VKRequest("account.getProfileInfo")

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

    open fun showPopup(view: View) {
        val popup = PopupMenu(this, view)
        val inflater: MenuInflater = popup.getMenuInflater()
        inflater.inflate(R.menu.menu, popup.getMenu())
        popup.show()
    }

    open fun onMenuItemClick(item: MenuItem) {
        val alertDialog: AlertDialog = AlertDialog.Builder(this).create()
        alertDialog.setTitle("Log Out?")
        alertDialog.setMessage("You will be logged out")

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Ok", { dialog, which -> logOut()  })
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Cancel", { dialog, which -> alertDialog.dismiss()  })
        alertDialog.show()
    }

    private fun getProfilePhoto() {

        var photoLink = "yopta"

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
                photoLink = user.getString("photo_max_orig").toString()

                Picasso.get().load(photoLink).into(profileAvatar)
                }
        })
    }
}


