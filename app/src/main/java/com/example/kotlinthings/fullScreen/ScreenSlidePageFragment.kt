package com.example.kotlinthings.fullScreen

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.kotlinthings.R
import com.example.kotlinthings.network.DownloadBitmapIntoImageView
import kotlinx.android.synthetic.main.fragment_full_screen_preview.*

class ScreenSlidePageFragment : Fragment() {

    companion object {
        fun newInstance(url: String): ScreenSlidePageFragment {
            val fragment = ScreenSlidePageFragment()
            val arguments = Bundle()
            arguments.putString("url", url)
            fragment.arguments = arguments
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(
            R.layout.fragment_full_screen_preview,
            container, false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val url = arguments?.getString("url")

        if (url == null) Log.e(
            "ScreenSlidePageFragment",
            "IMAGE URL IS NULL"
        ) else DownloadBitmapIntoImageView(
            url,
            fullScreenImage
        ).execute()
    }
}




