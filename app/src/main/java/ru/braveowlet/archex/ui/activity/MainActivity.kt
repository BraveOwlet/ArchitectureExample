package ru.braveowlet.archex.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dagger.hilt.android.AndroidEntryPoint
import ru.braveowlet.archex.ui.theme.ArchitectureExampleTheme
import ru.braveowlet.oauth.ui.OAuthScreen

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ArchitectureExampleTheme {
                OAuthScreen()
            }
        }
    }
}