package com.example.kotlinthings.fullScreen

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.kotlinthings.Photos

class FullScreenPreviewFragmentAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {

        return ScreenSlidePageFragment.newInstance(Photos.ORIGSIZE.get(position))
    }

    override fun getCount(): Int {
        return Photos.ORIGSIZE.count()
    }

}