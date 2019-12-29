package com.example.kotlinthings.network

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.util.Log
import android.widget.ImageView
import com.example.kotlinthings.CacheManager.Companion.MEMORY_LRU_CACHE
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

class DownloadBitmapIntoImageView(imageUrl: String, imageView: ImageView): AsyncTask<Unit, Unit, Unit>(){

    private var _imageUrl= imageUrl
    private var _imageView = imageView
    private lateinit var bitMap: Bitmap

    override fun doInBackground(vararg params: Unit?){
        Log.i("Cache", "Cache size is: ${MEMORY_LRU_CACHE.getCacheElementCount()} items")

        val cachedBitmap = MEMORY_LRU_CACHE.getBitmapFromMemCache(_imageUrl)

        // get from cache instead of network
        if(cachedBitmap != null) {
            Log.i("Cache", "Image with url ..${_imageUrl.takeLast(10)} is already loaded in cache, returning cached bitmap")

            bitMap = cachedBitmap
        } else {

            try {
                val url = URL(_imageUrl)
                val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
                connection.doInput = true
                connection.useCaches = true
                connection.connect()
                val input: InputStream = connection.inputStream
                bitMap = BitmapFactory.decodeStream(input)

                // new
                MEMORY_LRU_CACHE.addBitmapToMemoryCache(_imageUrl, bitMap)
                Log.i("CACHE2", "NEW CACHE SIZE: ${MEMORY_LRU_CACHE.getCacheElementCount()}")
            }
            catch (e: IOException) {
                e.printStackTrace()
                Log.i("ImageLoader", "Error in getting image from url, url was: $_imageUrl")

            }
        }
    }

    override fun onPostExecute(result: Unit) {
        super.onPostExecute(result)
        _imageView.setImageBitmap(bitMap)
    }

}

class ImageLoader {

    private fun downloadImage(url: String) {}


}