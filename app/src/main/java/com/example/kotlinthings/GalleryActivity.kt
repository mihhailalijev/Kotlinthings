package com.example.kotlinthings

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vk.sdk.api.*
import com.vk.sdk.api.VKRequest.VKRequestListener
import org.json.JSONException
import org.json.JSONObject

open class GalleryActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    private val debugTag = "SDKdebug"

    // List of photo links
    var photoLinkArray = mutableListOf<String>()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)
    }

    fun getPhotos(view: View) {

        var requestParameters = VKParameters.from(
            VKApiConst.ALBUM_ID, "wall", // Get photos from user's wall
            VKApiConst.COUNT, "30") // Get max 5 photos

        // request = request method + parameters
        var request = VKRequest("photos.getAll", requestParameters)

        // execute request and wait for result
        request.executeWithListener(object : VKRequestListener() {
            override fun onComplete(response: VKResponse) {
                //Do complete stuff
                createPhotoLinkList(response) // send response to parse method
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

    fun displayPhotos(view: View) {

        displayGallery(photoLinkArray)
    }

    private fun createPhotoLinkList(response : VKResponse) {

        try {
            val `object` = JSONObject(response.responseString)
            val responseObject = `object`.getJSONObject("response")
            val array = responseObject.getJSONArray("items")

            // Clear the list, so there will be no duplicates
            photoLinkArray.clear()

            for (i in 0 until array.length()) {

                // item = Photo object with id and link
                var item = array.getJSONObject(i)


                var id = item.getInt("id") // Get id for photo
                var link = item["photo_75"].toString() // Get link for photo

                Log.i(debugTag, "PHOTO ID: $id")
                Log.i(debugTag, "PHOTO LINK: $link")

                Log.i(debugTag, "index i = $i link = $link")

                photoLinkArray.add(i, link) // CRASH
            }

        } catch (e: JSONException) {
            Log.i(debugTag, "Parsing Json's is just not my thing")
        }

        // Show text view with debug info
        showPhotoDebugInfo()
    }

    private fun showPhotoDebugInfo() {

        val textview =  findViewById(R.id.debugTextView) as TextView

        textview.text = "Photos are loaded, amount is: ${photoLinkArray.count()}"

    }

    private fun displayGallery(photoLinkArray: List<String>) {
        Log.i(debugTag, "displayGallery called")

        viewManager = GridLayoutManager(this,4)
        viewAdapter = GalleryAdapter(photoLinkArray)

        recyclerView = findViewById<RecyclerView>(R.id.galleryRecyclerView).apply {

            setHasFixedSize(true) // All photos are fixed size (size can be set, by using different link in response)

            layoutManager = viewManager
            adapter = viewAdapter
        }
    }
}



