package com.example.kotlinthings.gallery

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinthings.Photos
import com.example.kotlinthings.R
import com.squareup.picasso.Picasso

typealias OnItemClickListener = (id: String) -> Unit

class GalleryAdapter(private val onItemClickListener : OnItemClickListener) : RecyclerView.Adapter<GalleryAdapter.ViewHolder>() {

    private var photoLinkList = mutableListOf<String>()


    fun addAll(items: Collection<String>) {
        val oldListSize = itemCount
        photoLinkList.addAll(items)

        notifyItemRangeInserted(oldListSize, itemCount)
    }

  inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val imageView = view.findViewById<ImageView>(R.id.galleryPhotoCell)

        init {
            view.isFocusable = true
            view.isClickable = true
            view.setOnClickListener {
                onItemClickListener(Photos.ORIGSIZE.get(adapterPosition))
            }
        }
    }

      override fun onCreateViewHolder( parent: ViewGroup, viewType: Int) : ViewHolder {

        val imageView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.photo_cell_layout, parent, false) as View

        return ViewHolder(imageView)
        }

      override fun getItemCount(): Int {
                return photoLinkList.size
            }

      override fun onBindViewHolder(holder: ViewHolder, position: Int) {

            var photo = photoLinkList[position]
            Picasso.get().load(photo).into(holder.imageView)
        }
}


