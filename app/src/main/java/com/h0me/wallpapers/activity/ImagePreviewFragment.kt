package com.h0me.wallpapers.activity

import android.app.WallpaperManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.h0me.wallpapers.activity.CategoriesFragment.Companion.textArg
import com.h0me.wallpapers.databinding.FragmentImagePreviewBinding
import com.h0me.wallpapers.extensions.load
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.URL

class ImagePreviewFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentImagePreviewBinding.inflate(
            inflater,
            container,
            false
        )

        binding.photo.load(requireNotNull(requireArguments().textArg))

        binding.setButton.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                val inputStream = URL(requireArguments().textArg).openStream()
                WallpaperManager.getInstance(requireContext()).setStream(inputStream)
            }
        }

        return binding.root
    }
}
