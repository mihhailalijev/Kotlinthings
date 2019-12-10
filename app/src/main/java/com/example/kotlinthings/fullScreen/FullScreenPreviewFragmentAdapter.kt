package com.example.kotlinthings.fullScreen

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.kotlinthings.Photos

class FullScreenPreviewFragmentAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

  //  var photoLinkList = mutableListOf<String>()
    var photoLinkList = mutableListOf("https://sun9-57.userapi.com/c837731/v837731717/3e85/WJDldpirLYQ.jpg", "https://sun9-54.userapi.com/c9895/u37525717/-6/x_f682c063.jpg")

    override fun getItem(position: Int): Fragment {
        return ScreenSlidePageFragment.newInstance(Photos.INSTANCE.get(position))
    }

    override fun getCount(): Int {
        return Photos.INSTANCE.count()
    }



}