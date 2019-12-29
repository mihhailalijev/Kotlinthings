package com.example.kotlinthings.network

import com.example.kotlinthings.gallery.GalleryActivity
import com.vk.sdk.api.VKResponse
import org.json.JSONObject

class Parser {

    fun parseUserInfo(response: VKResponse): GalleryActivity.User {

        val jsonObject = JSONObject(response.responseString)
        val responseObject = jsonObject.getJSONObject("response")

        val name = responseObject["first_name"].toString()
        val lastName = responseObject["last_name"].toString()

        return GalleryActivity.User(name, lastName)
    }

    fun parseUserPhoto(response: VKResponse): String {
        lateinit var photoLink: String

        val resp = response.json.getJSONArray("response")
        val user = resp.getJSONObject(0)
        photoLink = user.getString("photo_max_orig").toString()
        return photoLink
    }

    fun parseUserPhotoCollection(response: VKResponse): Pair<List<String>, List<String>> {

        val thumbnails = mutableListOf<String>()
        val origins = mutableListOf<String>()

        val jsonObject = JSONObject(response.responseString)
        val responseObject = jsonObject.getJSONObject("response")
        val array = responseObject.getJSONArray("items")
        val photoSizes =
            mutableListOf("photo_130", "photo_604", "photo_807", "photo_1280", "photo_2560")

        for (i in 0 until array.length()) {
            val item = array.getJSONObject(i)

            // Orig. sizes
            for (photoSize in photoSizes) {
                if (item.has(photoSize)) {
                    thumbnails.add(item[photoSize].toString())
                    break
                }
            }

            // Thumbnails
            for (i in photoSizes.size - 1 downTo 0) {
                val photoSize = photoSizes[i]
                if (item.has(photoSize)) {
                    origins.add(item[photoSize].toString())
                    break
                }
            }
        }
        return Pair(thumbnails, origins)
    }
}