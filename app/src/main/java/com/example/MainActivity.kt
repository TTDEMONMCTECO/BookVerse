package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.data.api.ApiClient
import com.example.data.local.BookDatabase
import com.example.data.local.SettingsManager
import com.example.data.repository.BookRepository
import com.example.ui.MainScreen
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.viewmodel.BookViewModel
import com.example.ui.viewmodel.BookViewModelFactory

class MainActivity : ComponentActivity() {

    private val viewModel: BookViewModel by viewModels {
        val database = BookDatabase.getInstance(applicationContext)
        val repository = BookRepository(
            apiService = ApiClient.apiService,
            favoriteBookDao = database.favoriteBookDao()
        )
        val settingsManager = SettingsManager.getInstance(applicationContext)
        BookViewModelFactory(repository, settingsManager)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val currentTheme by viewModel.themeMode.collectAsState()

            MyApplicationTheme(themeMode = currentTheme) {
                Surface(modifier = Modifier.fillMaxSize()) {
                    MainScreen(viewModel = viewModel)
                }
            }
        }
    }
}
