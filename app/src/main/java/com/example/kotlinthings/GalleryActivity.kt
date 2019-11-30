package com.example.kotlinthings

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.vk.sdk.api.*
import com.vk.sdk.api.VKRequest.VKRequestListener
import org.json.JSONException
import org.json.JSONObject


open class GalleryActivity : AppCompatActivity() {

    val debugTag = "SDKdebug"
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)
    }

    fun getPhotos(view: View) {

        var requestParameters = VKParameters.from(
            VKApiConst.ALBUM_ID, "wall", // Get photos from user's wall
            VKApiConst.COUNT, "5") // Get max 5 photos

        // request = request method + parameters
        var request = VKRequest("photos.getAll", requestParameters)

        // execute request and wait for result
        request.executeWithListener(object : VKRequestListener() {
            override fun onComplete(response: VKResponse) {
                //Do complete stuff
                createPhotoArray(response) // send response to parse method
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

    private fun createPhotoArray(response : VKResponse) {

        var photoArray = arrayOf<String>()

        try {
            val `object` = JSONObject(response.responseString)
            val responseObject = `object`.getJSONObject("response")
            val array = responseObject.getJSONArray("items")

            for (i in 0 until array.length()) {

                // item = Photo object with id and link
                var item = array.getJSONObject(i)


                var id = item.getInt("id") // Get id for photo
                var link = item["photo_75"].toString() // Get link for photo

                Log.i(debugTag, "PHOTO ID: $id")
                Log.i(debugTag, "PHOTO LINK: $link")

                Log.i(debugTag, "index i = $i link = $link")

               photoArray.set(i, link) // CRASH
            }

        } catch (e: JSONException) {
            Log.i(debugTag, "Parsing Json's is just not your thing")
        }





    }
}



