package com.h0me.wallpapers.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.h0me.wallpapers.R
import com.h0me.wallpapers.adapter.CollectionAdapter
import com.h0me.wallpapers.databinding.FragmentCategoriesBinding
import com.h0me.wallpapers.listener.OnInteractionListenerImpl
import com.h0me.wallpapers.model.PhotoCollection
import com.h0me.wallpapers.utils.StringArg
import com.h0me.wallpapers.viewmodel.AppViewModel

class CategoriesFragment : Fragment() {

    private val viewModel: AppViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )

    private val interactionListener by lazy {
        object : OnInteractionListenerImpl(
            this@CategoriesFragment.requireActivity(), viewModel
        ) {
            override fun onOpenCollection(collection: PhotoCollection) {
                findNavController().navigate(
                    R.id.action_categoriesFragment_to_photosFragment,
                    Bundle().apply {
                        textArg = collection.title
                    })
            }
        }
    }

    private val queries = listOf(
//            "sky,phone,wallpaper",
//            "forest,phone,wallpaper",
//            "ocean,phone,wallpaper",
//            "city,phone,wallpaper",
//            "dogs,phone,wallpaper",
//            "cats,phone,wallpaper",
//            "mountains,phone,wallpaper",
//            "space,phone,wallpaper",
//            "village,phone,wallpaper",
//            "cyberpunk,phone,wallpaper",
//            "retro,phone,wallpaper",
//            "cars,phone,wallpaper",
//            "fish,phone,wallpaper",
//            "abstract,phone,wallpaper",
        "neon,phone,wallpaper"
    )


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentCategoriesBinding.inflate(
            inflater,
            container,
            false
        )


        val adapter = CollectionAdapter(interactionListener)

        viewModel.getCollections(queries)

        binding.list.adapter = adapter
        viewModel.dataCategories.observe(viewLifecycleOwner) { data ->
            adapter.submitList(data.data)
        }



        return binding.root
    }

    companion object {
        var Bundle.textArg: String? by StringArg
    }


}
