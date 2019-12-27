package com.example.kotlinthings.Network

import android.util.Log
import com.vk.sdk.api.VKApiConst
import com.vk.sdk.api.VKParameters
import com.vk.sdk.api.VKRequest
import com.vk.sdk.api.VKResponse

class Requests {


    fun getUserInfo(): VKResponse {
        val request = VKRequest("account.getProfileInfo")
        lateinit var response: VKResponse
        Log.i("ABC", "BEFORE EXECUTE")

        request.executeSyncWithListener(object : VKRequest.VKRequestListener() {
            override fun onComplete(vkResponse: VKResponse) {
                //Do complete stuff
                response = vkResponse
            }
        })
        return response
    }

    fun getUserPhoto(): VKResponse {
        lateinit var response: VKResponse

        var requestParameters = VKParameters.from(VKApiConst.FIELDS, "photo_max_orig")
        var request = VKRequest("users.get", requestParameters)

        request.executeSyncWithListener(object : VKRequest.VKRequestListener() {
            override fun onComplete(vkResponse: VKResponse) {
                response = vkResponse
            }

        })
        return response
    }

    fun getUserPhotoCollection(offset: Int = 0): VKResponse {

        lateinit var response: VKResponse

        val requestParameters = VKParameters.from(
            VKApiConst.ALBUM_ID, "wall",
            VKApiConst.COUNT, "50",
            VKApiConst.OFFSET, offset
        )

        val request = VKRequest("photos.getAll", requestParameters)

        request.executeSyncWithListener(object : VKRequest.VKRequestListener() {
            override fun onComplete(vkResponse: VKResponse) {
                response = vkResponse
            }
        })
        return response
    }

    private fun getResponse(response: VKResponse): VKResponse {
        return response
    }
}