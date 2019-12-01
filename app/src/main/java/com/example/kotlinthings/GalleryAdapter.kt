package com.example.kotlinthings

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class GalleryAdapter(photoLinkArray: List<String>) : RecyclerView.Adapter<GalleryAdapter.ViewHolder>() {

    var photoLinkArray = photoLinkArray

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

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

     //   Picasso.get().load(photo).into()
    }


}


