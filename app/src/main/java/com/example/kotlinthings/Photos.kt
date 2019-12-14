package com.example.kotlinthings

class Photos {

    companion object {

        val THUMBNAILS by lazy { Photos()
        }
        val ORIGSIZE by lazy {
            Photos()
        }

    }

    private val images = mutableListOf<String>()

    fun addAll(items: Collection<String>) {
        images.addAll(items)
    }

    fun get(position: Int) = images[position]

    fun count() = images.size

    fun getPosition(url: String): Int {

      return images.indexOf(url)

    }
}