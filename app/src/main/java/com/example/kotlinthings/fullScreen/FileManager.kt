package com.example.kotlinthings.fullScreen

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.os.Environment
import android.provider.MediaStore
import java.text.SimpleDateFormat
import java.util.*

class FileManager(private val context: Context) {

 fun saveFile(bitmapImage: Bitmap) {

     val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
     val currentDate = sdf.format(Date()).toString()

     val resolver = context.contentResolver
     val contentValues = ContentValues().apply {
         put(MediaStore.MediaColumns.DISPLAY_NAME, "no display name")
         put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
         put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
     }

     val uri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

     MediaStore.Images.Media.insertImage(resolver, bitmapImage, currentDate, "desc")

     resolver.openOutputStream(uri!!).use {
         // TODO something with the stream
     }
}
}