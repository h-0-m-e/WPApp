package com.h0me.wallpapers.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.h0me.wallpapers.R
import com.h0me.wallpapers.databinding.ActivityAppBinding


class AppActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityAppBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bottomNav: BottomNavigationView = findViewById(R.id.bottomNav)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        findViewById<BottomNavigationView>(R.id.bottomNav)
            .setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.photoPreviewFragment) {
                bottomNav.visibility = View.GONE
            } else {
                bottomNav.visibility = View.VISIBLE
            }

        }

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
    }
}
