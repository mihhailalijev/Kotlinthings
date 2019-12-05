package com.example.kotlinthings

import android.content.Intent
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vk.sdk.api.*
import com.vk.sdk.api.VKRequest.VKRequestListener
import kotlinx.android.synthetic.main.activity_gallery.*
import kotlinx.android.synthetic.main.photo_cell_layout.*
import org.json.JSONException
import org.json.JSONObject

open class GalleryActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    private val debugTag = "SDKdebug"

    // List of photo links
    var photoLinkList = mutableListOf<String>()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)

        if (photoLinkList.count() == 0) getAndDisplayPhotos()
    }

    private fun getAndDisplayPhotos() {

        var requestParameters = VKParameters.from(
            VKApiConst.ALBUM_ID, "wall", // Get photos from user's wall
            VKApiConst.COUNT, "50") // Get max 50 photos

        var request = VKRequest("photos.getAll", requestParameters)

        request.executeWithListener(object : VKRequestListener() {
            override fun onComplete(response: VKResponse) {
                //Do complete stuff
                Log.i(debugTag, "Before Array: ${photoLinkList.count()}")

                createPhotoLinkList(response) // send response to parse method

                Log.i(debugTag, "After array: ${photoLinkList.count()}")

                displayGallery(photoLinkList)
            }

            override fun onError(error: VKError?) {
                //Do error stuff
            }

            override fun attemptFailed(
                request: VKRequest?,
                attemptNumber: Int,
                totalAttempts: Int
            ) {

            }
        })
    }

    private fun createPhotoLinkList(response : VKResponse) {

        try {
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

        } catch (e: JSONException) {
            Log.i(debugTag, "Parsing Json's is just not my thing")
        }

        // Show text view with debug info
        showPhotoDebugInfo()
    }

    private fun showPhotoDebugInfo() {

        val textview =  findViewById(R.id.debugTextView) as TextView

        textview.text = "Photos are loaded, amount is: ${photoLinkList.count()}"
    }

    private fun displayGallery(photoLinkList: List<String>) {

        viewManager = GridLayoutManager(this,4)
        viewAdapter = GalleryAdapter(photoLinkList) { id ->

            val intent = Intent(this, FullScreenPreview::class.java )
            intent.putExtra("id", id)
            startActivity(intent)

        } // id -> photo id

        recyclerView = findViewById<RecyclerView>(R.id.galleryRecyclerView).apply {

            setHasFixedSize(true) // All photos are fixed size (size can be set, by using different link in response)

            layoutManager = viewManager
            adapter = viewAdapter
        }
    }
}



