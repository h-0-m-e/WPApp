package com.h0me.wallpapers.activity

import android.opengl.Visibility
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.h0me.wallpapers.R
import com.h0me.wallpapers.databinding.ActivityAppBinding


class AppActivity : AppCompatActivity() {
    private lateinit var bottomNav : BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityAppBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bottomNav = findViewById(R.id.bottomNav)
        bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.categories -> {
                    findNavController(R.id.nav_host_fragment).navigate(R.id.categoriesFragment)
                    true
                }
                R.id.favourite -> {
                    findNavController(R.id.nav_host_fragment).navigate(R.id.favouriteFragment)
                    true
                }
                R.id.downloaded -> {
                    findNavController(R.id.nav_host_fragment).navigate(R.id.downloadedFragment)
                    true
                }
                R.id.settings -> {
                    findNavController(R.id.nav_host_fragment).navigate(R.id.settingsFragment)
                    true
                }
                else -> false
            }
        }
//        binding.apply {
//            findNavController(R.id.nav_host_fragment).addOnDestinationChangedListener { _, nd: NavDestination, _ ->
//                if (nd.id == R.id.imagePreviewFragment) {
//                    bottomNav.visibility = View.VISIBLE
//                } else {
//                    bottomNav.visibility = View.GONE
//                }
//            }
//        }
    }
}
