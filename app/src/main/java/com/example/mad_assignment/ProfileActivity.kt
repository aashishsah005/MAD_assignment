package com.example.mad_assignment

import android.content.Intent
import android.os.Bundle
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView

class ProfileActivity : AppCompatActivity() {

    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        ThemeManager.applyTheme(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        val darkModeSwitch: Switch = findViewById(R.id.dark_mode_switch)
        darkModeSwitch.isChecked = ThemeManager.getCurrentTheme(this) == ThemeManager.Theme.DARK

        darkModeSwitch.setOnCheckedChangeListener { _, isChecked ->
            val newTheme = if (isChecked) ThemeManager.Theme.DARK else ThemeManager.Theme.LIGHT
            ThemeManager.setTheme(this, newTheme)
        }

        setupBottomNavigation()
    }

    override fun onResume() {
        super.onResume()
        bottomNavigationView.selectedItemId = R.id.nav_profile
        updateCartBadge()
    }

    private fun setupBottomNavigation() {
        bottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNavigationView.selectedItemId = R.id.nav_profile

        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    startActivity(Intent(this, HomeActivity::class.java))  // FIXED: was MainActivity
                    overridePendingTransition(0, 0)
                    finish()
                    true
                }
                R.id.nav_profile -> {
                    true
                }
                R.id.nav_cart -> {
                    startActivity(Intent(this, CartActivity::class.java))
                    overridePendingTransition(0, 0)
                    true
                }
                else -> false
            }
        }
    }

    private fun updateCartBadge() {
        val cartSize = CartManager.getCartSize()
        val badge = bottomNavigationView.getOrCreateBadge(R.id.nav_cart)

        if (cartSize > 0) {
            badge.isVisible = true
            badge.number = cartSize
        } else {
            badge.isVisible = false
        }
    }
}