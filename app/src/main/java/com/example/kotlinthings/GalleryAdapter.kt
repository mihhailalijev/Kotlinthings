package com.example.kotlinthings

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

typealias OnItemClickListener = (id: String) -> Unit

class GalleryAdapter(photoLinkList: List<String>, private val onItemClickListener : OnItemClickListener) : RecyclerView.Adapter<GalleryAdapter.ViewHolder>() {

    var photoLinkList = photoLinkList

  inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val imageView = view.findViewById<ImageView>(R.id.galleryPhotoCell)

        init {
            view.isFocusable = true
            view.isClickable = true
            view.setOnClickListener {
                onItemClickListener(photoLinkList[adapterPosition])
            }
        }
    }

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): ViewHolder {

            val imageView = LayoutInflater.from(parent.context)
                .inflate(R.layout.photo_cell_layout, parent, false) as View

            return ViewHolder(imageView)
        }

        override fun getItemCount(): Int {
            return photoLinkList.count()
        }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        var photo = photoLinkList[position]
        Picasso.get().load(photo).into(holder.imageView)
    }

}


