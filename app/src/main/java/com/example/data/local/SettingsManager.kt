package com.example.data.local

import android.content.Context
import android.content.SharedPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

enum class AppThemeMode(val title: String, val subtitle: String) {
    DARK("Dark Theme", "Immersive deep indigo for comfortable reading"),
    LIGHT("Light Theme", "Clean editorial paper aesthetic"),
    SYSTEM("System Default", "Matches your device display settings")
}

class SettingsManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("bookverse_settings", Context.MODE_PRIVATE)

    private val _themeMode = MutableStateFlow(loadThemeMode())
    val themeMode: StateFlow<AppThemeMode> = _themeMode.asStateFlow()

    private val _maxResults = MutableStateFlow(prefs.getInt(KEY_MAX_RESULTS, 20))
    val maxResults: StateFlow<Int> = _maxResults.asStateFlow()

    private val _autoSearch = MutableStateFlow(prefs.getBoolean(KEY_AUTO_SEARCH, true))
    val autoSearch: StateFlow<Boolean> = _autoSearch.asStateFlow()

    private fun loadThemeMode(): AppThemeMode {
        val saved = prefs.getString(KEY_THEME_MODE, AppThemeMode.DARK.name)
        return try {
            AppThemeMode.valueOf(saved ?: AppThemeMode.DARK.name)
        } catch (e: Exception) {
            AppThemeMode.DARK
        }
    }

    fun setThemeMode(mode: AppThemeMode) {
        prefs.edit().putString(KEY_THEME_MODE, mode.name).apply()
        _themeMode.value = mode
    }

    fun setMaxResults(count: Int) {
        prefs.edit().putInt(KEY_MAX_RESULTS, count).apply()
        _maxResults.value = count
    }

    fun setAutoSearch(enabled: Boolean) {
        prefs.edit().putBoolean(KEY_AUTO_SEARCH, enabled).apply()
        _autoSearch.value = enabled
    }

    companion object {
        private const val KEY_THEME_MODE = "pref_theme_mode"
        private const val KEY_MAX_RESULTS = "pref_max_results"
        private const val KEY_AUTO_SEARCH = "pref_auto_search"

        @Volatile
        private var INSTANCE: SettingsManager? = null

        fun getInstance(context: Context): SettingsManager {
            return INSTANCE ?: synchronized(this) {
                val instance = SettingsManager(context.applicationContext)
                INSTANCE = instance
                instance
            }
        }
    }
}
