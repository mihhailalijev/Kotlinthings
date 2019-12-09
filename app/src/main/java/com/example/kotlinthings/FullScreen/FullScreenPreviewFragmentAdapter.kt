package com.example.kotlinthings.FullScreen

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter


class FullScreenPreviewFragmentAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    var photoLinkList = mutableListOf<String>()



    override fun getItem(position: Int): Fragment {

        Log.i("SDKdebug", "Adapter, getItem called")

        return ScreenSlidePageFragment()
    }

    override fun getCount(): Int {
        Log.i("SDKdebug", "Adapter, getCount, amount of items: ${photoLinkList.size}")
        return photoLinkList.size
    }

}