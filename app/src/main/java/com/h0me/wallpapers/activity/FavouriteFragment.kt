package com.h0me.wallpapers.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.h0me.wallpapers.databinding.FragmentFavouriteBinding

class FavouriteFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentFavouriteBinding.inflate(
            inflater,
            container,
            false
        )

        return binding.root
    }
}
