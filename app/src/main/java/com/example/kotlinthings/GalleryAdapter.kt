package com.example.kotlinthings

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

typealias OnItemClickListener = (id: String) -> Unit

class GalleryAdapter(photoLinkArray: List<String>, private val onItemClickListener : OnItemClickListener) : RecyclerView.Adapter<GalleryAdapter.ViewHolder>() {

    var photoLinkArray = photoLinkArray

  inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {



        val imageView = view.findViewById<ImageView>(R.id.galleryPhotoCell)

        init {
            view.isFocusable = true
            view.isClickable = true
            view.setOnClickListener {
                onItemClickListener(photoLinkArray[adapterPosition])
            }
        }

    }

        // Create new views (invoked by the layout manager)
        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int


        ): ViewHolder {

            val imageView = LayoutInflater.from(parent.context)
                .inflate(R.layout.photo_cell_layout, parent, false) as View

            return ViewHolder(imageView)
        }

        override fun getItemCount(): Int {
            return photoLinkArray.count()
        }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        var photo = photoLinkArray[position]

        var position = holder.adapterPosition

        Log.i("ADAPTER POSITION:", "$position")
        Log.i("ITEM COUNT:", "$itemCount")

        if(holder.adapterPosition == itemCount) {
            Log.i("SDKdebug1","POSITION = LAST ELEMENT")

        }

        Picasso.get().load(photo).into(holder.imageView)

    }

}


