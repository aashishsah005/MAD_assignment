package com.example.mad_assignment

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate

object ThemeManager {

    private const val PREFERENCES_NAME = "ThemePrefs"
    private const val KEY_THEME = "app_theme"

    enum class Theme {
        LIGHT, DARK, SYSTEM
    }

    fun applyTheme(context: Context) {
        val sharedPreferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
        when (sharedPreferences.getString(KEY_THEME, Theme.SYSTEM.name)) {
            Theme.LIGHT.name -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            Theme.DARK.name -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            else -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        }
    }

    fun setTheme(context: Context, theme: Theme) {
        val sharedPreferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
        sharedPreferences.edit().putString(KEY_THEME, theme.name).apply()

        when (theme) {
            Theme.LIGHT -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            Theme.DARK -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            Theme.SYSTEM -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        }
    }

    fun getCurrentTheme(context: Context): Theme {
        val sharedPreferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
        val themeName = sharedPreferences.getString(KEY_THEME, Theme.SYSTEM.name)
        return Theme.valueOf(themeName ?: Theme.SYSTEM.name)
    }
}
