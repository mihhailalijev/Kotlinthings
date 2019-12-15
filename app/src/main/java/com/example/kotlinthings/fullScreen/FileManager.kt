package com.example.kotlinthings.fullScreen

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity

class FileManager(private var context: Context) {

 fun saveFile(bitmapImage: Bitmap, share: Boolean = false) {

     var imageTitle = "image_${Math.random()}"

     var values = ContentValues()
     values.put(MediaStore.Images.Media.TITLE, imageTitle)
     values.put(MediaStore.Images.Media.MIME_TYPE, "image/JPEG")

     var resolver = context.contentResolver
     var uri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)

     val imageOut = resolver.openOutputStream(uri!!)
     imageOut.use { imageOut ->
         bitmapImage.compress(Bitmap.CompressFormat.JPEG, 100, imageOut)
     }

     if (share) {
         var sendIntent: Intent = Intent().apply {
             action = Intent.ACTION_SEND
             putExtra(Intent.EXTRA_STREAM, uri)
             putExtra(Intent.EXTRA_TEXT, "Shared via kotlinthings app <3")
             type = "image/JPEG"
         }
         Log.i("SHARE", "URI: $uri")
         startActivity(context, sendIntent, null)
     }
 }
}
