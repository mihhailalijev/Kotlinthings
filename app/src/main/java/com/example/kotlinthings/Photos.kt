package com.example.kotlinthings

class Photos {

    companion object {

        val INSTANCE by lazy { Photos()
        }

    }

    private val images = mutableListOf<String>()

    fun addAll(items: Collection<String>) {
        images.addAll(items)
    }

    fun get(position: Int) = images[position]

    fun count() = images.size

}