package com.example.kotlinthings.Gallery

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

typealias OnScroll = () -> Unit

class ScrollListener(
    private val viewManager: GridLayoutManager,
    private val scrollListener: OnScroll
) : RecyclerView.OnScrollListener() {

    private var isFetchingData = false

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val firstVisibleItem = viewManager.findFirstVisibleItemPosition()
        val visibleItemCount = viewManager.childCount

        if(dy > 0 && (firstVisibleItem + visibleItemCount) >= viewManager.itemCount){

           synchronized(this) {
            if(!isFetchingData) {
                isFetchingData = true
                scrollListener()
            }
        }
    }
    }

    @Synchronized
    fun dataFetched(){
        isFetchingData = false
    }

}