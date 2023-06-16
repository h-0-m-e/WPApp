package com.h0me.wallpapers.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.h0me.wallpapers.R
import com.h0me.wallpapers.activity.CategoriesFragment.Companion.textArg
import com.h0me.wallpapers.adapter.PhotoAdapter
import com.h0me.wallpapers.databinding.FragmentPhotosBinding
import com.h0me.wallpapers.listener.OnInteractionListenerImpl
import com.h0me.wallpapers.model.Photo
import com.h0me.wallpapers.viewmodel.AppViewModel

class PhotosFragment : Fragment() {

    private val viewModel: AppViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )

    private val interactionListener by lazy {
        object : OnInteractionListenerImpl(
            this@PhotosFragment.requireContext(), viewModel
        ) {
            override fun onOpenPhoto(photo: Photo) {
                findNavController().navigate(
                    R.id.action_photosFragment_to_photoPreviewFragment,
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
        val binding = FragmentPhotosBinding.inflate(
            inflater,
            container,
            false
        )

        viewModel.getCollection(requireNotNull(requireArguments().textArg))

        val adapter = PhotoAdapter(interactionListener)

        binding.list.adapter = adapter
        viewModel.dataPhotos.observe(viewLifecycleOwner) { data ->
            adapter.submitList(data)
        }

        return binding.root
    }
}
