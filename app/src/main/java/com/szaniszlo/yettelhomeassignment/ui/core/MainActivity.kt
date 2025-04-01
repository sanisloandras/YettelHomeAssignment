package com.szaniszlo.yettelhomeassignment.ui.core

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.szaniszlo.yettelhomeassignment.ui.core.theme.YettelAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            YettelAppTheme {
                // todo dark theme is not configured
                YettelAppTheme(darkTheme = false) {
                    YettelNavHost()
                }
            }
        }
    }
}
