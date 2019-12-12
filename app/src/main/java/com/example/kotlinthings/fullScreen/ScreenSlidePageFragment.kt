package com.example.kotlinthings.fullScreen

import com.example.kotlinthings.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.kotlinthings.Photos
import com.squareup.picasso.Picasso
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
        Picasso.get().load(url).into(fullScreenImage)
    }
}




