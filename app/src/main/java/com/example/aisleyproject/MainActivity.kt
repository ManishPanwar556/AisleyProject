package com.example.aisleyproject

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.example.aisleyproject.databinding.ActivityMainBinding
import com.example.aisleyproject.viewModel.AisleyViewModel

class MainActivity : AppCompatActivity() {
    private val viewModel by viewModels<AisleyViewModel>()
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        changestatusBarIconColor()
        addBadges()
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainer) as NavHostFragment
        val navController = navHostFragment.navController
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.dashBoardFragment) {
                binding.bottomNav.visibility = View.VISIBLE
            } else {
                binding.bottomNav.visibility = View.GONE
            }

        }
    }

    private fun changestatusBarIconColor() {
        if (Build.VERSION.SDK_INT >= 23) {
            val decor = this.window.decorView
            if (decor.systemUiVisibility != View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR) {
                decor.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            } else {
                decor.systemUiVisibility = 0
            }
        }
    }

    private fun addBadges() {
        binding.apply {
            val dashBoardItem=bottomNav.getOrCreateBadge(R.id.dashBoardFragment)
            dashBoardItem.number = 9
            dashBoardItem.backgroundColor =
                ResourcesCompat.getColor(resources, R.color.purple, null)

            val matchesItem= bottomNav.getOrCreateBadge(R.id.matches)

         matchesItem.number=50
            matchesItem.backgroundColor =
                ResourcesCompat.getColor(resources, R.color.purple, null)
        }


    }
}