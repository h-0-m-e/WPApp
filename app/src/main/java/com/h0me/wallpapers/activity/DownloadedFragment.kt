package com.h0me.wallpapers.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.h0me.wallpapers.R
import com.h0me.wallpapers.activity.CategoriesFragment.Companion.textArg
import com.h0me.wallpapers.adapter.PhotoAdapter
import com.h0me.wallpapers.databinding.FragmentDownloadedBinding
import com.h0me.wallpapers.listener.OnInteractionListenerImpl
import com.h0me.wallpapers.model.Photo
import com.h0me.wallpapers.viewmodel.AppViewModel

class DownloadedFragment : Fragment() {

    private val viewModel: AppViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )

    private val interactionListener by lazy {
        object : OnInteractionListenerImpl(
            this@DownloadedFragment.requireActivity(), viewModel
        ) {
            override fun onOpenPhoto(photo: Photo) {
                findNavController().navigate(
                    R.id.action_downloadedFragment_to_photoPreviewFragment,
                    Bundle().apply {
                        textArg = photo.url.full
                    })

            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentDownloadedBinding.inflate(
            inflater,
            container,
            false
        )

        val adapter = PhotoAdapter(interactionListener)

        binding.list.adapter = adapter
        viewModel.dataDownloaded.observe(viewLifecycleOwner) { data ->
            adapter.submitList(data)
            binding.isEmptyText.isVisible = data.isEmpty()
        }

        return binding.root
    }
}
